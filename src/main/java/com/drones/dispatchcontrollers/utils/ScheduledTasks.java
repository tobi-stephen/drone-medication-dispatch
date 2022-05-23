package com.drones.dispatchcontrollers.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.drones.dispatchcontrollers.model.Drone;
import com.drones.dispatchcontrollers.model.enums.DroneState;
import com.drones.dispatchcontrollers.repository.DroneRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author tobi
 * @created 21/05/2022
 */

@Component
public class ScheduledTasks {

    private final DroneRepository droneRepository;

    @Autowired
    public ScheduledTasks(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 20000)
    public void checkDronesForBadState() {
        // This task is used to change state of drones periodically if it is in LOADING state and less than 25%

        log.info("Drone Bad State Check at: {}", dateFormat.format(new Date()));

        List<Drone> drones = droneRepository.findDronesInBadState(DroneState.LOADING.toString(), Constants.ALLOWABLE_BATT_CAPACITY_FOR_LOADING);
        for (Drone drone: drones) {
            if (drone.getWeightOccupied() > 0) {
                drone.setState(DroneState.LOADED.toString());
            } else {
                drone.setState(DroneState.IDLE.toString());
            }
            droneRepository.save(drone);
        }

        log.info("Number of drones in bad state than {}%: {}", Constants.ALLOWABLE_BATT_CAPACITY_FOR_LOADING, drones.size());
    }

    @Scheduled(fixedRate = 20000)
    public void updateDronesToLoadingState() {
        // This task is used to change state of drones periodically if it is in IDLE state and greater than 25%
        // This periodic enables drones to be available for LOADING

        log.info("Changing Drones to LOADING State at: {}", dateFormat.format(new Date()));

        List<Drone> drones = droneRepository.findDronesByStateAndBatteryCapacity(DroneState.IDLE.toString(), Constants.ALLOWABLE_BATT_CAPACITY_FOR_LOADING);
        for (Drone drone: drones) {
            drone.setState(DroneState.LOADING.toString());
            droneRepository.save(drone);
        }

        log.info("Number of drones changed to LOADING state than {}%: {}", Constants.ALLOWABLE_BATT_CAPACITY_FOR_LOADING, drones.size());
    }

    @Scheduled(fixedRate = 20000)
    public void checkDroneBatteryLevel() throws IOException {
        // This task is used to check all drone's battery level and log it for auditing

        Date date = new Date();
        log.info("Drone Battery Level Check at: {}", dateFormat.format(date));
        List<Drone> drones = droneRepository.findAll();
        String result = StringUtils.join(drones, "\n");
        File file = new File("logs");
        file.mkdir();
        String filename = String.format("logs/drones_%d_%d_%d_%d_%d_%d.csv", date.getYear()+1900, date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
        try (PrintWriter writer = new PrintWriter(new File (filename))) {
            writer.write(result);
        } catch (FileNotFoundException e) {
            log.info(e.getMessage());
        }

        log.info("Number of drones found than {}%: {}", Constants.ALLOWABLE_BATT_CAPACITY_FOR_LOADING, drones.size());
    }
}

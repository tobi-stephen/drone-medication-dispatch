package com.drones.dispatchcontrollers.service.impl;

import com.drones.dispatchcontrollers.model.Drone;
import com.drones.dispatchcontrollers.model.Response;
import com.drones.dispatchcontrollers.model.enums.DroneState;
import com.drones.dispatchcontrollers.model.enums.ResponseCodes;
import com.drones.dispatchcontrollers.repository.DroneRepository;
import com.drones.dispatchcontrollers.service.DroneService;
import com.drones.dispatchcontrollers.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author tobi
 * @created 19/05/2022
 */

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public Response<Drone> createOrUpdateEntity(Drone model) {
        if (model.getId() > 0) {
            if (!droneRepository.existsById(model.getId())) {
                return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), "Invalid Drone ID: " + String.valueOf(model.getId()), null);
            }
            model.setDateCreated(droneRepository.findById(model.getId()).get().getDateCreated());
        } else {
            if (droneRepository.findBySerialNumber(model.getSerialNumber()).isPresent()) {
                return Response.build(ResponseCodes.RESOURCE_ALREADY_EXISTS.getCode(), "Resource already exists: " + model.getSerialNumber(), null);
            }
            model.setState(DroneState.IDLE.toString());
            model.setWeightOccupied(0);
        }
        if (model.getState().equalsIgnoreCase(DroneState.LOADING.toString()) && model.getBatteryCapacity() < 25) {
            return Response.build(ResponseCodes.BAD_REQUEST.getCode(), "Battery capacity low", null);
        }

        model = droneRepository.save(model);
        return Response.build(model);
    }

    @Override
    public Response<Drone> fetchEntity(long id) {
        Optional<Drone> drone = droneRepository.findById(id);
        if (!drone.isPresent()) {
            return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), ResponseCodes.ENTITY_NOT_FOUND.getMessage(), null);
        }
        return Response.build(drone.get());
    }

    @Override
    public Response<List<Drone>> fetchAllEntities() {
        List<Drone> drones = droneRepository.findAll();
        return Response.build(drones);
    }

    @Override
    public Response deleteEntity(long id) {
        return null;
    }

    @Override
    public Response<List<Drone>> fetchDronesForLoading() {
        List<Drone> drones = droneRepository.findDronesByStateAndBatteryCapacity(DroneState.LOADING.toString(), Constants.ALLOWABLE_BATT_CAPACITY_FOR_LOADING);
        return Response.build(drones);
    }

    @Override
    public Response<Drone> fetchDroneBySerialNumber(String serialNumber) {
        Optional<Drone> drone = droneRepository.findBySerialNumber(serialNumber);
        if (!drone.isPresent()) {
            return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), ResponseCodes.ENTITY_NOT_FOUND.getMessage(), null);
        }
        return Response.build(drone.get());
    }
}

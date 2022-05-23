package com.drones.dispatchcontrollers.repository;

import com.drones.dispatchcontrollers.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author tobi
 * @created 19/05/2022
 */

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    Optional<Drone> findBySerialNumber(String serialNumber);
    List<Drone> findAllByState(String state);

    @Query("SELECT d FROM Drone d WHERE d.state = ?1 AND d.batteryCapacity >= ?2")
    List<Drone> findDronesByStateAndBatteryCapacity(String state, long batteryCapacity);
    @Query("SELECT d FROM Drone d WHERE d.state = ?1 AND d.batteryCapacity < ?2")
    List<Drone> findDronesInBadState(String state, long batteryCapacity);

}

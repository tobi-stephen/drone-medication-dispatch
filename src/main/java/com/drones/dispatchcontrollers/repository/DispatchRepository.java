package com.drones.dispatchcontrollers.repository;

import com.drones.dispatchcontrollers.model.Dispatch;
import com.drones.dispatchcontrollers.model.Drone;
import com.drones.dispatchcontrollers.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author tobi
 * @created 21/05/2022
 */

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Long> {

    List<Dispatch> findAllByDroneId(long droneId);
    Optional<Dispatch> findByMedicationId(long medicationId);
    List<Dispatch> findAllByDroneIdOrMedicationId(long droneId, long medicationId);

    @Query("SELECT m FROM Dispatch d JOIN Medication m ON d.medication.id = m.id WHERE d.drone.id = ?1")
    List<Medication> findMedicationsLoadedOnDrone(long droneId);
}

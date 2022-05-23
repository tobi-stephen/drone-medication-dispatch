package com.drones.dispatchcontrollers.repository;

import com.drones.dispatchcontrollers.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author tobi
 * @created 21/05/2022
 */

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Optional<Medication> findByCode(String code);
    List<Medication> findAllByName(String name);
}

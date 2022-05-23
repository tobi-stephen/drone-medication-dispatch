package com.drones.dispatchcontrollers.service.impl;

import com.drones.dispatchcontrollers.model.Dispatch;
import com.drones.dispatchcontrollers.model.Drone;
import com.drones.dispatchcontrollers.model.Medication;
import com.drones.dispatchcontrollers.model.Response;
import com.drones.dispatchcontrollers.model.enums.DroneState;
import com.drones.dispatchcontrollers.model.enums.ResponseCodes;
import com.drones.dispatchcontrollers.repository.DispatchRepository;
import com.drones.dispatchcontrollers.repository.DroneRepository;
import com.drones.dispatchcontrollers.repository.MedicationRepository;
import com.drones.dispatchcontrollers.service.DispatchService;
import com.drones.dispatchcontrollers.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author tobi
 * @created 21/05/2022
 */

@Service
public class DispatchServiceImpl implements DispatchService {

    private final DroneRepository droneRepository;
    private final DispatchRepository dispatchRepository;
    private final MedicationRepository medicationRepository;

    @Autowired
    public DispatchServiceImpl(DroneRepository droneRepository, DispatchRepository dispatchRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.dispatchRepository = dispatchRepository;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public Response<Dispatch> createOrUpdateEntity(Dispatch model) {
        if (model.getId() > 0) {
            if (!dispatchRepository.existsById(model.getId())) {
                return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), "Invalid Dispatch ID: " + String.valueOf(model.getId()), null);
            }
        } else {
            Drone drone = droneRepository.findById(model.getDrone().getId()).get();
            Medication medication = medicationRepository.findById(model.getMedication().getId()).get();

            if (drone == null || medication == null) {
                return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), "Invalid Request", null);
            }

            if (!drone.getState().equalsIgnoreCase(DroneState.LOADING.toString()) || drone.getBatteryCapacity() < Constants.ALLOWABLE_BATT_CAPACITY_FOR_LOADING) {
                return Response.build(ResponseCodes.SYSTEM_ERROR.getCode(), "Drone not ready for loading", null);
            }

            long weightLimit = drone.getWeightLimit();
            long weightOccupied = drone.getWeightOccupied();
            long medicationWeight = medication.getWeight();

            if (medicationWeight + weightOccupied > weightLimit) {
                return Response.build(ResponseCodes.SYSTEM_ERROR.getCode(), "Drone weight limit exceeded", null);
            }

            if(dispatchRepository.findByMedicationId(medication.getId()).isPresent()) {
                return Response.build(ResponseCodes.SYSTEM_ERROR.getCode(), "Medication already in dispatch", null);
            }

            drone.setWeightOccupied(medicationWeight + weightOccupied);
            if (drone.getWeightOccupied() == weightLimit) {
                drone.setState(DroneState.LOADED.toString());
            }
            droneRepository.save(drone);
            model.setCompleted(false);
            model.setDrone(drone);
            model.setMedication(medication);
        }

        model = dispatchRepository.save(model);
        return Response.build(model);
    }

    @Override
    public Response<Dispatch> fetchEntity(long id) {
        Optional<Dispatch> dispatch = dispatchRepository.findById(id);
        if (!dispatch.isPresent()) {
            return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), ResponseCodes.ENTITY_NOT_FOUND.getMessage(), null);
        }
        return Response.build(dispatch.get());
    }

    @Override
    public Response<List<Dispatch>> fetchAllEntities() {
        List<Dispatch> dispatches = dispatchRepository.findAll();
        return Response.build(dispatches);
    }

    @Override
    public Response deleteEntity(long id) {
        return null;
    }

    @Override
    public Response<List<Dispatch>> fetchDispatchByDroneIdOrMedicationId(long droneId, long medicationId) {
        List<Dispatch> dispatches = dispatchRepository.findAllByDroneIdOrMedicationId(droneId, medicationId);
        return Response.build(dispatches);
    }

    @Override
    public Response<Dispatch> completeDispatchForDrone(long id) {
        if (!dispatchRepository.existsById(id)) {
            return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), "Invalid Dispatch ID: " + String.valueOf(id), null);
        }

        Dispatch dispatch = dispatchRepository.findById(id).get();
        if (dispatch.getCompleted()) {
            return Response.build(ResponseCodes.SUCCESS.getCode(), "Dispatch already completed", dispatch);
        }
        Drone drone = droneRepository.findById(dispatch.getDrone().getId()).get();
        drone.setWeightOccupied(0);
        drone.setState(DroneState.DELIVERED.toString());
        droneRepository.save(drone);

        dispatch.setCompleted(true);
        dispatch = dispatchRepository.save(dispatch);

        return Response.build(dispatch);
    }

    @Override
    public Response<List<Medication>> fetchMedicationsLoadedOnDrone(long droneId) {
        List<Medication> medications = dispatchRepository.findMedicationsLoadedOnDrone(droneId);
        return Response.build(medications);
    }
}

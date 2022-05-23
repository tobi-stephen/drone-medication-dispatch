package com.drones.dispatchcontrollers.service;

import com.drones.dispatchcontrollers.model.Dispatch;
import com.drones.dispatchcontrollers.model.Medication;
import com.drones.dispatchcontrollers.model.Response;

import java.util.List;

/**
 * @author tobi
 * @created 21/05/2022
 */

public interface DispatchService extends BaseCrudService<Dispatch>{
    Response<List<Dispatch>> fetchDispatchByDroneIdOrMedicationId(long droneId, long medicationId);

    Response<Dispatch> completeDispatchForDrone(long id);

    Response<List<Medication>> fetchMedicationsLoadedOnDrone(long droneId);
}

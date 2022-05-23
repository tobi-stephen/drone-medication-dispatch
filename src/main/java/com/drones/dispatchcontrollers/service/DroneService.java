package com.drones.dispatchcontrollers.service;

import com.drones.dispatchcontrollers.model.Drone;
import com.drones.dispatchcontrollers.model.Response;

import java.util.List;

/**
 * @author tobi
 * @created 19/05/2022
 */

public interface DroneService extends BaseCrudService<Drone>{
    Response<List<Drone>> fetchDronesForLoading();

    Response<Drone> fetchDroneBySerialNumber(String serialNumber);
}

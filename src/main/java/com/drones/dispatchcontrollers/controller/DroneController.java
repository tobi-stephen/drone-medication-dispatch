package com.drones.dispatchcontrollers.controller;

import com.drones.dispatchcontrollers.model.Drone;
import com.drones.dispatchcontrollers.model.Response;
import com.drones.dispatchcontrollers.model.dto.DroneDto;
import com.drones.dispatchcontrollers.service.DroneService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author tobi
 * @created 19/05/2022
 */

@RestController
@RequestMapping("api/v1/drones")
public class DroneController {

    private final ModelMapper modelMapper;
    private final DroneService droneService;

    @Autowired
    public DroneController(ModelMapper modelMapper, DroneService droneService) {
        this.modelMapper = modelMapper;
        this.droneService = droneService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<Drone> registerOrUpdateDrone(@Valid @RequestBody DroneDto droneDto) {
        return droneService.createOrUpdateEntity(modelMapper.map(droneDto, Drone.class));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Drone> fetchDroneById(@PathVariable("id") long id) {
        return droneService.fetchEntity(id);
    }

    @GetMapping(path = "/serial-number/{sn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Drone> fetchDroneBySerialNumber(@PathVariable("sn") String serialNumber) {
        return droneService.fetchDroneBySerialNumber(serialNumber);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Drone>> fetchAllDrones() {
        return droneService.fetchAllEntities();
    }

    @GetMapping(path = "/loading", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Drone>> fetchDronesForLoading() {
        return droneService.fetchDronesForLoading();
    }
}

package com.drones.dispatchcontrollers.controller;

import com.drones.dispatchcontrollers.model.Dispatch;
import com.drones.dispatchcontrollers.model.Medication;
import com.drones.dispatchcontrollers.model.Response;
import com.drones.dispatchcontrollers.model.dto.DispatchDto;
import com.drones.dispatchcontrollers.service.DispatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author tobi
 * @created 21/05/2022
 */

@RestController
@RequestMapping("api/v1/dispatches")
public class DispatchController {

    private final ModelMapper modelMapper;
    private final DispatchService dispatchService;

    @Autowired
    public DispatchController(ModelMapper modelMapper, DispatchService dispatchService) {
        this.modelMapper = modelMapper;
        this.dispatchService = dispatchService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Response<Dispatch> registerOrUpdateDispatch(@Valid @RequestBody DispatchDto dispatchDto){
        return dispatchService.createOrUpdateEntity(modelMapper.map(dispatchDto, Dispatch.class));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Dispatch> fetchDispatchById(@PathVariable("id") long id) {
        return dispatchService.fetchEntity(id);
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Dispatch>> fetchAllDispatchByDroneOrMedication(@RequestParam(name = "droneId", defaultValue = "0", required = false) long droneId,
                                                                        @RequestParam(name = "medicationId", defaultValue = "0", required = false) long medicationId) {
        return dispatchService.fetchDispatchByDroneIdOrMedicationId(droneId, medicationId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Dispatch>> fetchAllDispatches() {
        return dispatchService.fetchAllEntities();
    }

    @GetMapping(path = "/complete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Dispatch> completeDispatch(@PathVariable("id") long id) {
        return dispatchService.completeDispatchForDrone(id);
    }

    @GetMapping(path = "/loaded/{droneId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Medication>> fetchMedicationsLoadedOnDrone(@PathVariable("droneId") long droneId) {
        return dispatchService.fetchMedicationsLoadedOnDrone(droneId);
    }

}

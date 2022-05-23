package com.drones.dispatchcontrollers.controller;

import com.drones.dispatchcontrollers.model.Medication;
import com.drones.dispatchcontrollers.model.Response;
import com.drones.dispatchcontrollers.model.dto.MedicationDto;
import com.drones.dispatchcontrollers.service.MedicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author tobi
 * @created 21/05/2022
 */

@RestController
@RequestMapping("api/v1/medications")
public class MedicationController {

    private final ModelMapper modelMapper;
    private final MedicationService medicationService;

    @Autowired
    public MedicationController(ModelMapper modelMapper, MedicationService medicationService) {
        this.modelMapper = modelMapper;
        this.medicationService = medicationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<Medication> registerOrUpdateMedication(@Valid @ModelAttribute MedicationDto medicationDto, @RequestPart("image") MultipartFile multipartFile) throws IOException {
        return medicationService.createOrUpdateEntity(modelMapper.map(medicationDto, Medication.class), multipartFile);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Medication> fetchMedicationById(@PathVariable("id") long id) {
        return medicationService.fetchEntity(id);
    }

    @GetMapping(path = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Medication> fetchMedicationByCode(@PathVariable("code") String code) {
        return medicationService.fetchMedicationByCode(code);
    }

    @GetMapping(path = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Medication>> fetchAllMedicationsByName(@PathVariable("name") String name) {
        return medicationService.fetchAllMedicationsByName(name);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Medication>> fetchAllMedications() {
        return medicationService.fetchAllEntities();
    }

}

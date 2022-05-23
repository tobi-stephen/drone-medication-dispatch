package com.drones.dispatchcontrollers.service.impl;

import com.drones.dispatchcontrollers.model.Medication;
import com.drones.dispatchcontrollers.model.Response;
import com.drones.dispatchcontrollers.model.enums.ResponseCodes;
import com.drones.dispatchcontrollers.repository.MedicationRepository;
import com.drones.dispatchcontrollers.service.MedicationService;
import com.drones.dispatchcontrollers.utils.Constants;
import com.drones.dispatchcontrollers.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author tobi
 * @created 21/05/2022
 */

@Service
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;


    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Override
    public Response<Medication> createOrUpdateEntity(Medication model, MultipartFile multipartFile) throws IOException {
        if (model.getId() > 0) {
            if (!medicationRepository.existsById(model.getId())) {
                return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), "Invalid Medication ID: " + String.valueOf(model.getId()), null);
            }
        } else {
            if (medicationRepository.findByCode(model.getCode()).isPresent()) {
                return Response.build(ResponseCodes.RESOURCE_ALREADY_EXISTS.getCode(), "Resource already exists: " + model.getCode(), null);
            }
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        model.setImage(fileName);
        model = medicationRepository.save(model);

        String uploadDir = Constants.BASE_UPLOAD_PATH + model.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return Response.build(model);
    }

    @Override
    public Response<Medication> createOrUpdateEntity(Medication model) {
        return null;
    }

    @Override
    public Response<Medication> fetchEntity(long id) {
        Optional<Medication> medication = medicationRepository.findById(id);
        if (!medication.isPresent()) {
            return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), ResponseCodes.ENTITY_NOT_FOUND.getMessage(), null);
        }
        return Response.build(medication.get());
    }

    @Override
    public Response<List<Medication>> fetchAllEntities() {
        List<Medication> medications = medicationRepository.findAll();
        return Response.build(medications);
    }

    @Override
    public Response deleteEntity(long id) {
        return null;
    }

    @Override
    public Response<Medication> fetchMedicationByCode(String code) {
        Optional<Medication> medication = medicationRepository.findByCode(code);
        if (!medication.isPresent()) {
            return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), ResponseCodes.ENTITY_NOT_FOUND.getMessage(), null);
        }
        return Response.build(medication.get());
    }

    @Override
    public Response<List<Medication>> fetchAllMedicationsByName(String name) {
        List<Medication> medications = medicationRepository.findAllByName(name);
        return Response.build(medications);
    }

}

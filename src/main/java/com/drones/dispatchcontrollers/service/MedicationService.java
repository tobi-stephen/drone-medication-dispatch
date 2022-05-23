package com.drones.dispatchcontrollers.service;

import com.drones.dispatchcontrollers.model.Medication;
import com.drones.dispatchcontrollers.model.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author tobi
 * @created 21/05/2022
 */

public interface MedicationService extends BaseCrudService<Medication>{
    Response<List<Medication>> fetchAllMedicationsByName(String name);

    Response<Medication> fetchMedicationByCode(String code);

    Response<Medication> createOrUpdateEntity(Medication model, MultipartFile multipartFile) throws IOException;
}

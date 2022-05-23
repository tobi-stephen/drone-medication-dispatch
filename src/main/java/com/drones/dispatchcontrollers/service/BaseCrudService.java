package com.drones.dispatchcontrollers.service;

import com.drones.dispatchcontrollers.model.Response;

import java.util.List;

/**
 * @author tobi
 * @created 19/05/2022
 */

public interface BaseCrudService<T> {
    Response<T> createOrUpdateEntity(T model);
    Response<T> fetchEntity(long id);
    Response<List<T>> fetchAllEntities();
    Response deleteEntity(long id);
}

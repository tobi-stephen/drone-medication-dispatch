package com.drones.dispatchcontrollers.model.dto;

import lombok.Data;

import javax.validation.constraints.*;


/**
 * @author tobi
 * @created 19/05/2022
 */

@Data
public class DispatchDto {
    private long id;

    @Positive(message = "Drone ID should be a valid ID")
    private long droneId;

    @Positive(message = "Medication ID should be a valid ID")
    private long medicationId;
}

package com.drones.dispatchcontrollers.model.dto;

import com.drones.dispatchcontrollers.model.enums.DroneState;
import lombok.Data;

import javax.validation.constraints.*;


/**
 * @author tobi
 * @created 19/05/2022
 */

@Data
public class DroneDto {
    private long id;

    @NotNull(message = "Serial Number is required.")
    @NotEmpty(message = "Serial Number cannot be empty.")
    @Size(min = 2, max = 100, message = "The length of serialNumber must be between 2 and 100 characters.")
    private String serialNumber;

    @NotNull(message = "Model is required.")
    @NotEmpty(message = "Model cannot be empty.")
    private String model;

    @Min(value = 10, message = "Weight Limit should be at least 10gr")
    @Max(value = 500, message = "Weight Limit max is 500gr")
    private long weightLimit;

    @PositiveOrZero(message = "Battery Capacity can not be negative")
    @Max(value = 100, message = "Battery Capacity max is 100%")
    private long batteryCapacity;

    private DroneState state;
}

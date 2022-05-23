package com.drones.dispatchcontrollers.model.dto;

import lombok.Data;

import javax.validation.constraints.*;


/**
 * @author tobi
 * @created 21/05/2022
 */

@Data
public class MedicationDto {
    private long id;

    @NotNull(message = "Code is required.")
    @NotEmpty(message = "Code cannot be empty.")
    @Size(min = 2, max = 100, message = "The length of serialNumber must be between 2 and 100 characters.")
    private String code;

    @NotNull(message = "Name is required.")
    @NotEmpty(message = "Name cannot be empty.")
    private String name;

    @Positive(message = "Weight should be at least 1gr")
    @Max(value = 500, message = "Weight max is 500gr")
    private long weight;
}

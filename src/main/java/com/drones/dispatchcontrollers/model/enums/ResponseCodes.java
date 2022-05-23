package com.drones.dispatchcontrollers.model.enums;

import lombok.Getter;

/**
 * @author tobi
 * @created 19/05/2022
 */

public enum ResponseCodes {

    SUCCESS("00", "Successful"),
    ENTITY_NOT_FOUND("06", "No record found for your request."),
    SYSTEM_ERROR("91", "Something went wrong. Please try again later"),
    BAD_REQUEST("90", "Bad Request"),
    RESOURCE_ALREADY_EXISTS("99", "Resource already exists"),
    INSUFFICIENT_PERMISSION("93", "Access denied."),
    NO_CONTENT("94", "No Content");

    private @Getter
    String code;

    private @Getter
    String message;

    ResponseCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.drones.dispatchcontrollers.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.drones.dispatchcontrollers.model.enums.ResponseCodes;
import lombok.Data;

/**
 * @author tobi
 * @created 19/05/2022
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private String code;
    private String message;
    private T data;

    public static <T>Response<T> build(T data){
        Response<T> response = new Response<>();
        response.setCode(ResponseCodes.SUCCESS.getCode());
        response.setMessage(ResponseCodes.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    public static <T>Response<T> build(String responseCode, String message, T data){
        Response<T> response = new Response<>();
        response.setCode(responseCode);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T>Response<T> buildBadRequest(){
        Response<T> response = new Response<>();
        response.setCode(ResponseCodes.BAD_REQUEST.getCode());
        response.setMessage(ResponseCodes.BAD_REQUEST.getMessage());
        response.setData(null);
        return response;
    }
}

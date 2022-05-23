package com.drones.dispatchcontrollers.controller.advice;

import com.drones.dispatchcontrollers.model.Response;
import com.drones.dispatchcontrollers.model.enums.ResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tobi
 * @created 21/05/2022
 */

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class ApiAdvice {
    private final Logger logger = LoggerFactory.getLogger(ApiAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleOtherExceptions(Exception e){
        Response response = new Response();
        response.setCode(ResponseCodes.SYSTEM_ERROR.getCode());
        response.setMessage(e.getMessage());
        logger.error(e.getMessage(), e);
        return response;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleIOExceptions(Exception e){
        Response response = new Response();
        response.setCode(ResponseCodes.SYSTEM_ERROR.getCode());
        response.setMessage(e.getMessage());
        logger.error(e.getMessage(), e);
        return response;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleDataIntegrityViolationException(DataIntegrityViolationException e){
        Response response = new Response();
        response.setCode(ResponseCodes.SYSTEM_ERROR.getCode());
        response.setMessage(e.getRootCause().getMessage());
        logger.error(e.getMessage(),e);
        return response;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleNullPointerException(NullPointerException e){
        Response response = new Response();
        response.setCode(ResponseCodes.BAD_REQUEST.getCode());
        response.setMessage(e.toString());
        logger.error(e.getMessage(), e);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected Response handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        Response<List<String>> response = processFieldErrors(fieldErrors);
        logger.error(e.getMessage(), e);

        return response;
    }

    private Response processFieldErrors(List<FieldError> fieldErrors) {
        Response<List<String>> response = new Response<>();
        response.setCode(ResponseCodes.BAD_REQUEST.getCode());
        response.setMessage(ResponseCodes.BAD_REQUEST.getMessage());
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError: fieldErrors) {
            errors.add(fieldError.getDefaultMessage());
        }
        response.setData(errors);
        return response;
    }
}


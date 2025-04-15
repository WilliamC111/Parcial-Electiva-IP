package com.virtualcoffee.orders_api.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends BaseException {
    private final List<String> errors;
    
    public ValidationException(String message, List<FieldError> fieldErrors) {
        super(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", message);
        this.errors = fieldErrors.stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }
    
    public List<String> getErrors() {
        return errors;
    }
}
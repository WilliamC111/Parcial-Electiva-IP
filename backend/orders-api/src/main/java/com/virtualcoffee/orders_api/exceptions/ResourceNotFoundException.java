package com.virtualcoffee.orders_api.exceptions;


import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", message);
    }
    
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", 
              String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
package com.virtualcoffee.orders_api.exceptions;

import org.springframework.http.HttpStatus;

public class ExternalServiceException extends BaseException {
    public ExternalServiceException(String serviceName, String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, "EXTERNAL_SERVICE_ERROR", 
              String.format("Error calling %s service: %s", serviceName, message));
    }
    
    public ExternalServiceException(String serviceName, String message, Throwable cause) {
        super(HttpStatus.SERVICE_UNAVAILABLE, "EXTERNAL_SERVICE_ERROR", 
              String.format("Error calling %s service: %s", serviceName, message), cause);
    }
}
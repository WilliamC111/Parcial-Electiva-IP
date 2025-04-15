package com.virtualcoffee.orders_api.exceptions;


import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, "CONFLICT", message);
    }
    
    public ConflictException(String resourceName, String conflictReason) {
        super(HttpStatus.CONFLICT, "CONFLICT", 
              String.format("Conflict with %s: %s", resourceName, conflictReason));
    }
}
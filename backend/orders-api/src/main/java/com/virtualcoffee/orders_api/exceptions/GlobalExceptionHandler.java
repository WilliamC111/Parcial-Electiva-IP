package com.virtualcoffee.orders_api.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus().value());
        body.put("error", ex.getStatus().getReasonPhrase());
        body.put("code", ex.getErrorCode());
        body.put("message", ex.getMessage());
        
        if (ex instanceof ValidationException) {
            body.put("errors", ((ValidationException) ex).getErrors());
        }
        
        return new ResponseEntity<>(body, ex.getStatus());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        ValidationException validationException = new ValidationException(
                "Validation failed", ex.getBindingResult().getFieldErrors());
        return handleBaseException(validationException, request);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        BaseException baseException = new BaseException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "INTERNAL_SERVER_ERROR", 
                "An unexpected error occurred: " + ex.getMessage(), 
                ex) {};
        return handleBaseException(baseException, request);
    }
}
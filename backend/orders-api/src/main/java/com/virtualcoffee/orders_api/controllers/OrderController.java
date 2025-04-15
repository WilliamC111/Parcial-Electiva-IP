package com.virtualcoffee.orders_api.controllers;

import com.virtualcoffee.orders_api.dtos.*;
import com.virtualcoffee.orders_api.exceptions.*;
import com.virtualcoffee.orders_api.services.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            OrderResponseDTO response = orderService.createOrder(orderRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (ExternalServiceException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // --- Clase interna para manejar errores ---
  // En OrderController.java
@Data
@Builder
public static class ErrorResponse {
    private final String message;
    @Builder.Default
    private final LocalDateTime timestamp = LocalDateTime.now();

    // Constructor necesario para Jackson
    public ErrorResponse(String message, LocalDateTime timestamp) {
        this.message = message;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    // Getters explícitos (necesarios para la prueba)
    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
}
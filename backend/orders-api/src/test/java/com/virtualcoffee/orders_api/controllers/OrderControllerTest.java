package com.virtualcoffee.orders_api.controllers;

import com.virtualcoffee.orders_api.dtos.*;
import com.virtualcoffee.orders_api.exceptions.*;
import com.virtualcoffee.orders_api.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;
    
    @InjectMocks
    private OrderController orderController;
    
    private OrderRequestDTO validRequest;
    private OrderResponseDTO successResponse;
    
    @BeforeEach
    public void setUp() {
        // Configuración de request válido
        OrderItemDTO item = new OrderItemDTO();
        item.setDrinkName("Latte");
        item.setSize("Medium");
        item.setQuantity(1);
        validRequest = new OrderRequestDTO();
        validRequest.setItems(List.of(item));
        
        // Configuración de response exitoso
        OrderResponseDTO.OrderItemResponse itemResponse = new OrderResponseDTO.OrderItemResponse();
        itemResponse.setDrinkName("Latte");
        itemResponse.setSize("Medium");
        itemResponse.setQuantity(1);
        itemResponse.setPrice(3.99);
        
        successResponse = new OrderResponseDTO();
        successResponse.setId("order-123");
        successResponse.setItems(List.of(itemResponse));
        successResponse.setStatus("PENDING");
        successResponse.setTotal(3.99);
    }
    
    @Test
    public void createOrder_ShouldReturnCreatedResponse() {
        when(orderService.createOrder(validRequest)).thenReturn(successResponse);
        
        ResponseEntity<?> response = orderController.createOrder(validRequest);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(successResponse, response.getBody());
    }
    
    @Test
    public void createOrder_ShouldHandleResourceNotFoundException() {
        when(orderService.createOrder(validRequest))
            .thenThrow(new ResourceNotFoundException("Drink not found"));
        
        ResponseEntity<?> response = orderController.createOrder(validRequest);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof OrderController.ErrorResponse);
    }
    
    @Test
    public void getAllOrders_ShouldReturnListOfOrders() {
        when(orderService.getAllOrders()).thenReturn(List.of(successResponse));
        
        ResponseEntity<List<OrderResponseDTO>> response = orderController.getAllOrders();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }
}
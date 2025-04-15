package com.virtualcoffee.orders_api.controllers;

import com.virtualcoffee.orders_api.dtos.*;
import com.virtualcoffee.orders_api.exceptions.ResourceNotFoundException;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;
    
    @InjectMocks
    private OrderController orderController;
    
    private OrderRequestDTO orderRequestDTO;
    private OrderResponseDTO orderResponseDTO;
    
    @BeforeEach
    public void setUp() {
        // Configuración del OrderItemDTO
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setDrinkName("Latte");
        itemDTO.setSize("Medium");
        itemDTO.setQuantity(1);
        
        // Configuración del OrderRequestDTO
        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setItems(List.of(itemDTO));
        
        // Configuración del OrderItemResponse
        OrderResponseDTO.OrderItemResponse itemResponse = new OrderResponseDTO.OrderItemResponse();
        itemResponse.setDrinkName("Latte");
        itemResponse.setSize("Medium");
        itemResponse.setQuantity(1);
        itemResponse.setPrice(3.99);
        itemResponse.setImageUrl("http://example.com/latte.jpg");
        
        // Configuración del OrderResponseDTO
        orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId("order-123");
        orderResponseDTO.setItems(List.of(itemResponse));
        orderResponseDTO.setStatus("PENDING");
        orderResponseDTO.setTotal(3.99);
    }
    
    @Test
    public void createOrder_ShouldReturnNotFoundWhenDrinkNotAvailable() {
        OrderItemDTO invalidItem = new OrderItemDTO();
        invalidItem.setDrinkName("UnknownDrink");
        invalidItem.setSize("Medium");
        invalidItem.setQuantity(1);
        
        OrderRequestDTO invalidRequest = new OrderRequestDTO();
        invalidRequest.setItems(List.of(invalidItem));
        
        when(orderService.createOrder(invalidRequest))
            .thenThrow(new ResourceNotFoundException("Drink not found"));
        
        ResponseEntity<?> response = orderController.createOrder(invalidRequest);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        
        // Verificación correcta del ErrorResponse
        assertTrue(response.getBody() instanceof OrderController.ErrorResponse);
        
        OrderController.ErrorResponse errorResponse = (OrderController.ErrorResponse) response.getBody();
        assertEquals("Drink not found", errorResponse.getMessage());
        assertNotNull(errorResponse.getTimestamp());
    }
    
@Test
public void getAllOrders_ShouldReturnEmptyListWhenNoOrders() {
    when(orderService.getAllOrders()).thenReturn(List.of());
    
    ResponseEntity<List<OrderResponseDTO>> response = orderController.getAllOrders();
    
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().isEmpty());
}
}
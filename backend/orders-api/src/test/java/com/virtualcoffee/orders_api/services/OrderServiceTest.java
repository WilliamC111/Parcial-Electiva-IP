package com.virtualcoffee.orders_api.services;

import com.virtualcoffee.orders_api.dtos.*;
import com.virtualcoffee.orders_api.exceptions.*;
import com.virtualcoffee.orders_api.models.*;
import com.virtualcoffee.orders_api.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    
    @Mock
    private DrinkServiceClient drinkServiceClient;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private OrderService orderService;
    
    private OrderRequestDTO orderRequestDTO;
    private OrderItemDTO orderItemDTO;
    private DrinkServiceClient.DrinkInfo drinkInfo;
    private Order order;
    private OrderResponseDTO orderResponseDTO;
    
    @BeforeEach
    public void setUp() {
        // Configuración del OrderItemDTO
        orderItemDTO = new OrderItemDTO();
        orderItemDTO.setDrinkName("Latte");
        orderItemDTO.setSize("Medium");
        orderItemDTO.setQuantity(2);
        
        // Configuración del OrderRequestDTO
        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setItems(List.of(orderItemDTO));
        
        // Configuración de la respuesta de bebidas
        drinkInfo = new DrinkServiceClient.DrinkInfo();
        drinkInfo.setName("Latte");
        drinkInfo.setAvailableSizes(Map.of("Small", 2.99, "Medium", 3.99, "Large", 4.99));
        drinkInfo.setImageUrl("http://example.com/latte.jpg");
        
        // Configuración de la Order
        OrderItem orderItem = new OrderItem();
        orderItem.setDrinkName("Latte");
        orderItem.setSize("Medium");
        orderItem.setQuantity(2);
        orderItem.setPrice(3.99);
        orderItem.setImageUrl("http://example.com/latte.jpg");
        
        order = new Order();
        order.setItems(List.of(orderItem));
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        
        // Configuración del OrderResponseDTO
        OrderResponseDTO.OrderItemResponse itemResponse = new OrderResponseDTO.OrderItemResponse();
        itemResponse.setDrinkName("Latte");
        itemResponse.setSize("Medium");
        itemResponse.setQuantity(2);
        itemResponse.setPrice(3.99);
        itemResponse.setImageUrl("http://example.com/latte.jpg");
        
        orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId("order-123");
        orderResponseDTO.setItems(List.of(itemResponse));
        orderResponseDTO.setStatus("PENDING");
        orderResponseDTO.setOrderDate(LocalDateTime.now());
        orderResponseDTO.setTotal(7.98);
    }
    
    @Test
    public void createOrder_ShouldRejectOrderWhenDrinkNotFound() {
        // Configurar mock para simular que la bebida no existe
        when(drinkServiceClient.getDrinkInfo("UnknownDrink"))
            .thenThrow(new ResourceNotFoundException("Drink not found"));
        
        OrderItemDTO unknownItem = new OrderItemDTO();
        unknownItem.setDrinkName("UnknownDrink");
        unknownItem.setSize("Medium");
        unknownItem.setQuantity(1);
        
        OrderRequestDTO invalidRequest = new OrderRequestDTO();
        invalidRequest.setItems(List.of(unknownItem));
        
        // Verificar que se lanza la excepción correcta
        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.createOrder(invalidRequest);
        });
        
        // Verificar que no se llamó a save
        verify(orderRepository, never()).save(any());
    }
    
    @Test
    public void createOrder_ShouldRejectOrderWhenSizeNotAvailable() {
        when(drinkServiceClient.getDrinkInfo("Latte")).thenReturn(drinkInfo);
        
        OrderItemDTO invalidSizeItem = new OrderItemDTO();
        invalidSizeItem.setDrinkName("Latte");
        invalidSizeItem.setSize("ExtraLarge"); // Tamaño no disponible
        invalidSizeItem.setQuantity(1);
        
        OrderRequestDTO invalidRequest = new OrderRequestDTO();
        invalidRequest.setItems(List.of(invalidSizeItem));
        
        // Verificar que se lanza la excepción correcta
        assertThrows(ValidationException.class, () -> {
            orderService.createOrder(invalidRequest);
        });
        
        // Verificar que no se llamó a save
        verify(orderRepository, never()).save(any());
    }
    
}
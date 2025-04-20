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
    
    private OrderRequestDTO validRequest;
    private OrderItemDTO validItem;
    private DrinkServiceClient.DrinkInfo drinkInfo;
    private Order savedOrder;
    private OrderResponseDTO expectedResponse;
    
    @BeforeEach
    public void setUp() {
        // Configuración de item válido
        validItem = new OrderItemDTO();
        validItem.setDrinkName("Latte");
        validItem.setSize("Medium");
        validItem.setQuantity(2);
        
        // Configuración de request válido
        validRequest = new OrderRequestDTO();
        validRequest.setItems(List.of(validItem));
        
        // Configuración de información de bebida
        drinkInfo = new DrinkServiceClient.DrinkInfo();
        drinkInfo.setName("Latte");
        drinkInfo.setAvailableSizes(Map.of("Medium", 3.99));
        drinkInfo.setImageUrl("http://example.com/latte.jpg");
        
        // Configuración de Order guardada
        OrderItem orderItem = new OrderItem();
        orderItem.setDrinkName("Latte");
        orderItem.setSize("Medium");
        orderItem.setQuantity(2);
        orderItem.setPrice(3.99);
        orderItem.setImageUrl("http://example.com/latte.jpg");
        
        savedOrder = new Order();
        savedOrder.setItems(List.of(orderItem));
        savedOrder.setStatus("PENDING");
        
        // Configuración de response esperado
        OrderResponseDTO.OrderItemResponse itemResponse = new OrderResponseDTO.OrderItemResponse();
        itemResponse.setDrinkName("Latte");
        itemResponse.setSize("Medium");
        itemResponse.setQuantity(2);
        itemResponse.setPrice(3.99);
        itemResponse.setImageUrl("http://example.com/latte.jpg");
        
        expectedResponse = new OrderResponseDTO();
        expectedResponse.setItems(List.of(itemResponse));
        expectedResponse.setStatus("PENDING");
        expectedResponse.setTotal(7.98); // 3.99 * 2
    }
    
    @Test
    public void createOrder_ShouldSaveOrderWhenValid() {
        when(drinkServiceClient.getDrinkInfo("Latte")).thenReturn(drinkInfo);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        when(modelMapper.map(savedOrder, OrderResponseDTO.class)).thenReturn(expectedResponse);
        
        OrderResponseDTO response = orderService.createOrder(validRequest);
        
        assertNotNull(response);
        assertEquals(7.98, response.getTotal());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
    
    @Test
    public void createOrder_ShouldThrowWhenDrinkNotFound() {
        when(drinkServiceClient.getDrinkInfo("UnknownDrink"))
            .thenThrow(new ResourceNotFoundException("Drink not found"));
        
        OrderItemDTO invalidItem = new OrderItemDTO();
        invalidItem.setDrinkName("UnknownDrink");
        invalidItem.setSize("Medium");
        invalidItem.setQuantity(1);
        
        OrderRequestDTO invalidRequest = new OrderRequestDTO();
        invalidRequest.setItems(List.of(invalidItem));
        
        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.createOrder(invalidRequest);
        });
        
        verify(orderRepository, never()).save(any());
    }
    
    @Test
    public void getAllOrders_ShouldReturnEmptyListWhenNoOrders() {
        // Configuración
        when(orderRepository.findAll()).thenReturn(List.of());
        
        // Ejecución
        List<OrderResponseDTO> result = orderService.getAllOrders();
        
        // Verificación
        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findAll();
        
        // Elimina esta línea si no es necesaria:
        // verify(modelMapper, never()).map(any(), any());
    }
}
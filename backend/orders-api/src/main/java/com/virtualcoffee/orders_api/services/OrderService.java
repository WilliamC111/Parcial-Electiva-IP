package com.virtualcoffee.orders_api.services;

import com.virtualcoffee.orders_api.dtos.*;
import com.virtualcoffee.orders_api.exceptions.*;
import com.virtualcoffee.orders_api.models.*;
import com.virtualcoffee.orders_api.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final DrinkServiceClient drinkServiceClient;
    
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, 
                      DrinkServiceClient drinkServiceClient) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.drinkServiceClient = drinkServiceClient;
    }
    
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        // Validación inicial de la estructura del pedido
        if (orderRequestDTO.getItems() == null || orderRequestDTO.getItems().isEmpty()) {
            throw new ValidationException("Order must contain at least one item", List.of());
        }

        Order order = new Order();
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        
        List<OrderItem> items = orderRequestDTO.getItems().stream()
            .map(itemDTO -> {
                // Validaciones básicas del item
                validateOrderItem(itemDTO);
                
                // Consultar API de bebidas
                DrinkServiceClient.DrinkInfo drinkInfo = getDrinkInfoWithValidation(itemDTO);
                
                // Crear y retornar el OrderItem
                return createOrderItem(itemDTO, drinkInfo);
            })
            .collect(Collectors.toList());
        
        order.setItems(items);
        Order savedOrder = orderRepository.save(order);
        return convertToDtoWithTotal(savedOrder);
    }
    
    private void validateOrderItem(OrderItemDTO itemDTO) {
        if (itemDTO.getDrinkName() == null || itemDTO.getDrinkName().trim().isEmpty()) {
            throw new ValidationException("Drink name cannot be empty", List.of());
        }
        if (itemDTO.getSize() == null || itemDTO.getSize().trim().isEmpty()) {
            throw new ValidationException("Size cannot be empty", List.of());
        }
        if (itemDTO.getQuantity() == null || itemDTO.getQuantity() < 1) {
            throw new ValidationException("Quantity must be at least 1", List.of());
        }
    }
    
    private DrinkServiceClient.DrinkInfo getDrinkInfoWithValidation(OrderItemDTO itemDTO) {
        try {
            DrinkServiceClient.DrinkInfo drinkInfo = drinkServiceClient.getDrinkInfo(itemDTO.getDrinkName());
            
            if (drinkInfo == null) {
                throw new ResourceNotFoundException(
                    "Drink not found: " + itemDTO.getDrinkName()
                );
            }
            
            if (!drinkInfo.getAvailableSizes().containsKey(itemDTO.getSize())) {
                throw new ValidationException(
                    "Size " + itemDTO.getSize() + " not available for drink " + itemDTO.getDrinkName(),
                    List.of()
                );
            }
            
            return drinkInfo;
            
        } catch (ExternalServiceException e) {
            throw new ExternalServiceException(
                "Drinks Service", 
                "Failed to validate drink availability: " + e.getMessage(),
                e
            );
        }
    }
    
    private OrderItem createOrderItem(OrderItemDTO itemDTO, DrinkServiceClient.DrinkInfo drinkInfo) {
        OrderItem item = new OrderItem();
        item.setDrinkName(itemDTO.getDrinkName());
        item.setSize(itemDTO.getSize());
        item.setQuantity(itemDTO.getQuantity());
        item.setPrice(drinkInfo.getAvailableSizes().get(itemDTO.getSize()));
        item.setImageUrl(drinkInfo.getImageUrl());
        return item;
    }
    
    private OrderResponseDTO convertToDtoWithTotal(Order order) {
        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);
        
        // Calcular el total basado en los items
        double total = order.getItems().stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
        
        dto.setTotal(total);
        return dto;
    }
    
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
            .map(this::convertToDtoWithTotal)
            .collect(Collectors.toList());
    }
}
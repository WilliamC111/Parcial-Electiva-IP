package com.virtualcoffee.orders_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class OrderItemDTO {
    @NotBlank(message = "Drink name is required")
    private String drinkName;
    
    @NotBlank(message = "Size is required")
    private String size;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
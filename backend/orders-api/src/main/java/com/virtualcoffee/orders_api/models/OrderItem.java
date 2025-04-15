package com.virtualcoffee.orders_api.models;

import lombok.Data;

@Data
public class OrderItem {
    private String drinkName;
    private String size;
    private Integer quantity; // Usar Integer en lugar de int
    private Double price;    // Usar Double en lugar de double
    private String imageUrl;
}
package com.virtualcoffee.orders_api.models;

import lombok.Data;

@Data
public class OrderItem {
    private String drinkName;
    private String size;
    private Integer quantity; 
    private Double price;   
    private String imageUrl;
}
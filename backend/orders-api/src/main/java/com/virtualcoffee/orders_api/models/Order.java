package com.virtualcoffee.orders_api.models;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private String id;
    private List<OrderItem> items;
    private LocalDateTime orderDate;
    private String status;
    private Double total;
}
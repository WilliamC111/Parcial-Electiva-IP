package com.virtualcoffee.orders_api.repositories;

import java.util.List;
import java.util.Optional;

import com.virtualcoffee.orders_api.models.Order;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> findById(String id);
    Order save(Order order);
    void deleteById(String id);
}
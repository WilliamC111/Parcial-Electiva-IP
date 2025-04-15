package com.virtualcoffee.orders_api.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.virtualcoffee.orders_api.models.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private List<Order> orders = new ArrayList<>();

    @Value("${orders.file.path}")
    private String filePath;

    private final ObjectMapper objectMapper;

    public OrderRepositoryImpl(@Value("${orders.file.path}") String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // ¡Esto es crucial!
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @PostConstruct
    public void init() {
        try {
            File file = new File(filePath);
            System.out.println("Ruta completa del archivo: " + file.getAbsolutePath());
            System.out.println("Archivo existe?: " + file.exists());

            if (file.exists()) {
                String jsonContent = Files.readString(file.toPath());
                System.out.println("Contenido del archivo: " + jsonContent);

                orders = objectMapper.readValue(file, new TypeReference<List<Order>>() {});
                System.out.println("Órdenes cargadas: " + orders.size());
            }
        } catch (Exception e) {
            System.err.println("Error cargando órdenes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            objectMapper.writeValue(new File(filePath), orders);
        } catch (IOException e) {
            System.err.println("Error guardando órdenes en archivo: " + e.getMessage());
        }
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public Optional<Order> findById(String id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(UUID.randomUUID().toString());
            order.setOrderDate(LocalDateTime.now());
            orders.add(order);
        } else {
            orders.removeIf(o -> o.getId().equals(order.getId()));
            orders.add(order);
        }
        saveToFile();
        return order;
    }

    @Override
    public void deleteById(String id) {
        orders.removeIf(o -> o.getId().equals(id));
        saveToFile();
    }
}

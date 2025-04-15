package com.virtualcoffee.orders_api.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private String id;
    private List<OrderItemResponse> items;
    private LocalDateTime orderDate;
    private String status;
    private Double total; // Cambiado a Double para mayor precisión

    @Data
    public static class OrderItemResponse {
        private String drinkName;
        private String size;
        private Integer quantity;
        private Double price;
        private String imageUrl;
    }
}
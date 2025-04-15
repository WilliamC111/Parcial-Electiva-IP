package com.virtualcoffee.orders_api.services;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.virtualcoffee.orders_api.exceptions.ExternalServiceException;

import lombok.Data;

@Service
public class DrinkServiceClient {
    @Value("${drinks.api.url}")
    private String drinksApiUrl;
    
    private final RestTemplate restTemplate;
    
    public DrinkServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public DrinkInfo getDrinkInfo(String drinkName) {
        String url = drinksApiUrl + "/menu/" + drinkName;
        try {
            return restTemplate.getForObject(url, DrinkInfo.class);
        } catch (Exception e) {
            throw new ExternalServiceException("Drinks API", e.getMessage(), e);
        }
    }
    
    @Data
    public static class DrinkInfo {
        private String name;
        private Map<String, Double> availableSizes;
        private String imageUrl;
    }
}
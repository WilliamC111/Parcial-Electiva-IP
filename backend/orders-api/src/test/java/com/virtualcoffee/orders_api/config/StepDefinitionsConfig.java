package com.virtualcoffee.orders_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.virtualcoffee.orders_api.services.DrinkServiceClient;
import org.mockito.Mockito;

@Configuration
public class StepDefinitionsConfig {
    
    @Bean
    public DrinkServiceClient drinkServiceClient() {
        return Mockito.mock(DrinkServiceClient.class);
    }
}
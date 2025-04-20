package com.virtualcoffee.orders_api.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class DrinkServiceClientTest {

    @Mock
    private RestTemplate restTemplate;
    
    @InjectMocks
    private DrinkServiceClient drinkServiceClient;
    
    @Test
    public void getDrinkInfo_ShouldReturnDrinkInfo() {
        // Configurar mock
        DrinkServiceClient.DrinkInfo mockResponse = new DrinkServiceClient.DrinkInfo();
        mockResponse.setName("Latte");
        mockResponse.setAvailableSizes(Map.of("Medium", 3.99));
        mockResponse.setImageUrl("http://example.com/latte.jpg");
        
        when(restTemplate.getForObject(anyString(), eq(DrinkServiceClient.DrinkInfo.class)))
            .thenReturn(mockResponse);
        
        // Ejecutar
        DrinkServiceClient.DrinkInfo result = drinkServiceClient.getDrinkInfo("Latte");
        
        // Verificar
        assertNotNull(result);
        assertEquals("Latte", result.getName());
        assertTrue(result.getAvailableSizes().containsKey("Medium"));
        verify(restTemplate, times(1))
            .getForObject(anyString(), eq(DrinkServiceClient.DrinkInfo.class));
    }
    
    @Test
    public void getDrinkInfo_ShouldThrowWhenServiceFails() {
        when(restTemplate.getForObject(anyString(), any()))
            .thenThrow(new RuntimeException("Service unavailable"));
        
        assertThrows(RuntimeException.class, () -> {
            drinkServiceClient.getDrinkInfo("Latte");
        });
    }
}
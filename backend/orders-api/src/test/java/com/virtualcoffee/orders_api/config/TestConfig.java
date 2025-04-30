package com.virtualcoffee.orders_api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(StepDefinitionsConfig.class) // Importa la otra configuración
public class TestConfig {
    // Elimina la definición duplicada de drinkServiceClient
}
package com.grupito.auth_service.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    static {
        // 🚀 ESTA LÍNEA ES MÁGICA: Fuerza a Spring Boot moderno a cambiar la ruta técnica
        System.setProperty("springdoc.openapi.api-docs.path", "/auth/v3/api-docs");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("/auth").description("Ruta Base a través del Gateway")
                ));
    }
}
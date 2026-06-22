package com.grupito.api_gateway.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String secret;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchanges -> exchanges
                // 1. Recursos y assets estáticos de interfaz (Evita el Whitelabel 404)
                .pathMatchers("/swagger-ui.html", "/swagger-ui/**", "/webjars/**", "/v3/api-docs/swagger-config", "/favicon.ico").permitAll()
                
                // 2. Especificaciones OpenAPI locales y de microservicios
                .pathMatchers("/v3/api-docs", "/v3/api-docs/**").permitAll()
                .pathMatchers("/*/v3/api-docs", "/*/v3/api-docs/**").permitAll()
                
                // 3. Endpoints públicos del sistema (Login y Registro)
                .pathMatchers("/auth/**").permitAll()
                
                // Todo lo demás de la lógica de negocio exige token JWT obligatorio
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> {})
            )
            .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        SecretKeySpec key = new SecretKeySpec(
            secret.getBytes(StandardCharsets.UTF_8),
            "HmacSHA256"
        );
        return NimbusReactiveJwtDecoder.withSecretKey(key).build();
    }
}
package com.grupito.pagos_services.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(WebClient.Builder webClientBuilder,
                         @Value("${usuarios.service.url}") String usuariosServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(usuariosServiceUrl).build();
    }

    public Mono<Map<String, Object>> getUsuarioById(Long id) {
        return webClient.get()
                .uri("/usuarios/{id}", id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    public Map<String, Object> getUsuarioByIdBlocking(Long id) {
        return getUsuarioById(id).block();
    }
}
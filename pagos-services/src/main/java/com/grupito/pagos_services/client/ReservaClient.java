package com.grupito.pagos_services.client;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ReservaClient {
    private final WebClient webClient;

    public ReservaClient(WebClient.Builder webClientBuilder, @Value("${reservas.service.url:http://reservas-service:9093}") String reservasServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(reservasServiceUrl).build();
    }

    public Mono<List<Map<String, Object>>> getReservasBySocio(Long idSocio) {
        return webClient.get()
                .uri("/reservas/por-socio/{idSocio}", idSocio)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {});
    }

    public List<Map<String, Object>> getReservasBySocioBlocking(Long idSocio) {
        return getReservasBySocio(idSocio).block();
    }
}
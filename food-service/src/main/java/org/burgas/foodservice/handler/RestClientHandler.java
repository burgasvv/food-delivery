package org.burgas.foodservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.foodservice.dto.Media;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientHandler {

    private final RestClient restClient;

    public RestClientHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "getMediaId", fallbackMethod = "fallBackGetMediaId")
    public ResponseEntity<Media> getMediaById(Long mediaId) {
        return restClient
                .get()
                .uri("http://localhost:9040/media/by-id?mediaId=" + mediaId)
                .retrieve()
                .toEntity(Media.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Media> fallBackGetMediaId(Throwable throwable) {
        return ResponseEntity.ok(Media.builder().build());
    }
}

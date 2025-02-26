package org.burgas.identityservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.identityservice.dto.IdentityPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class RestClientHandler {

    private final RestClient restClient;

    public RestClientHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    protected RestClient getRestClient() {
        return restClient;
    }

    @CircuitBreaker(name = "getIdentityPrincipal", fallbackMethod = "fallBackGetIdentityPrincipal")
    public ResponseEntity<IdentityPrincipal> getIdentityPrincipal(String authentication) {
        return restClient.get()
                .uri("http://localhost:8765/authentication/principal")
                .header(AUTHORIZATION, authentication)
                .accept(APPLICATION_JSON)
                .retrieve()
                .toEntity(IdentityPrincipal.class);
    }

    @SuppressWarnings("unused")
    public ResponseEntity<IdentityPrincipal> fallBackGetIdentityPrincipal(Throwable throwable) {
        return ResponseEntity.ok(IdentityPrincipal.builder().authenticated(false).build());
    }
}

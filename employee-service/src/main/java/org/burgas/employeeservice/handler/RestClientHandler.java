package org.burgas.employeeservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.employeeservice.dto.DepartmentResponse;
import org.burgas.employeeservice.dto.IdentityPrincipal;
import org.burgas.employeeservice.dto.IdentityResponse;
import org.burgas.employeeservice.dto.Media;
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

    @CircuitBreaker(name = "getIdentityResponse", fallbackMethod = "fallBackGetIdentityResponse")
    public ResponseEntity<IdentityResponse> getIdentityResponse(Long identityId, String authentication) {
        return restClient.get()
                .uri("http://localhost:8888/identities/by-id?identityId=" + identityId)
                .header(AUTHORIZATION, authentication)
                .retrieve()
                .toEntity(IdentityResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityResponse(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }

    @CircuitBreaker(name = "getDepartmentResponse", fallbackMethod = "fallBackGetDepartmentResponse")
    public ResponseEntity<DepartmentResponse> getDepartmentResponse(Long departmentId) {
        return restClient.get()
                .uri("http://localhost:9000/departments/by-id?departmentId=" + departmentId)
                .retrieve()
                .toEntity(DepartmentResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<DepartmentResponse> fallBackGetDepartmentResponse(Throwable throwable) {
        return ResponseEntity.ok(DepartmentResponse.builder().build());
    }

    @CircuitBreaker(name = "getMediaById", fallbackMethod = "fallBackGetMediaById")
    public ResponseEntity<Media> getMediaById(Long mediaId) {
        return restClient
                .get()
                .uri("http://localhost:9040/media/by-id?mediaId=" + mediaId)
                .retrieve()
                .toEntity(Media.class);
    }

    @SuppressWarnings("unused")
    public ResponseEntity<Media> fallBackGetMediaById(Throwable throwable) {
        return ResponseEntity.ok(Media.builder().build());
    }
}

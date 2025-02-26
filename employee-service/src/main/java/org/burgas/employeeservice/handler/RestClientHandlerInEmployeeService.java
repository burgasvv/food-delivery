package org.burgas.employeeservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.departmentservice.dto.DepartmentResponse;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.handler.RestClientHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientHandlerInEmployeeService extends RestClientHandler {

    public RestClientHandlerInEmployeeService(RestClient restClient) {
        super(restClient);
    }

    @CircuitBreaker(name = "getIdentityResponse", fallbackMethod = "fallBackGetIdentityResponse")
    public ResponseEntity<IdentityResponse> getIdentityResponse(Long identityId, String authentication) {
        return getRestClient().get()
                .uri("http://localhost:8888/identities/by-id?identityId=" + identityId)
                .header(HttpHeaders.AUTHORIZATION, authentication)
                .retrieve()
                .toEntity(IdentityResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityResponse> fallBackGetIdentityResponse(Throwable throwable) {
        return ResponseEntity.ok(IdentityResponse.builder().build());
    }

    @CircuitBreaker(name = "getDepartmentResponse", fallbackMethod = "fallBackGetDepartmentResponse")
    public ResponseEntity<DepartmentResponse> getDepartmentResponse(Long departmentId) {
        return getRestClient().get()
                .uri("http://localhost:9000/departments/by-id?departmentId=" + departmentId)
                .retrieve()
                .toEntity(DepartmentResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<DepartmentResponse> fallBackGetDepartmentResponse(Throwable throwable) {
        return ResponseEntity.ok(DepartmentResponse.builder().build());
    }
}

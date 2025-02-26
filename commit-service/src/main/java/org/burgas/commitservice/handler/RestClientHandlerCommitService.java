package org.burgas.commitservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.foodservice.dto.ComboResponse;
import org.burgas.foodservice.dto.FoodResponse;
import org.burgas.identityservice.handler.RestClientHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Component
public class RestClientHandlerCommitService extends RestClientHandler {

    public RestClientHandlerCommitService(RestClient restClient) {
        super(restClient);
    }

    @CircuitBreaker(name = "getFoodById", fallbackMethod = "fallBackGetFoodById")
    public ResponseEntity<FoodResponse> getFoodById(Long foodId) {
        return getRestClient()
                .get()
                .uri("http://localhost:9020/food/by-id?foodId=" + foodId)
                .retrieve()
                .toEntity(FoodResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<FoodResponse> fallBackGetFoodById(Throwable throwable) {
        return ResponseEntity.ok(FoodResponse.builder().build());
    }

    @CircuitBreaker(name = "getFoodByName", fallbackMethod = "fallBackGetFoodByName")
    public ResponseEntity<FoodResponse> getFoodByName(String name) {
        return getRestClient()
                .get()
                .uri("http://localhost:9020/food/by-name?name=" + name)
                .retrieve()
                .toEntity(FoodResponse.class);
    }


    @SuppressWarnings("unused")
    private ResponseEntity<FoodResponse> fallBackGetFoodByName(Throwable throwable) {
        return ResponseEntity.ok(FoodResponse.builder().build());
    }

    @CircuitBreaker(name = "getComboById", fallbackMethod = "fallBackByGetComboById")
    public ResponseEntity<ComboResponse> getComboById(Long comboId) {
        return getRestClient()
                .get()
                .uri("http://localhost:9020/combos/by-id?comboId=" + comboId)
                .retrieve()
                .toEntity(ComboResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<ComboResponse> fallBackByGetComboById(Throwable throwable) {
        return ResponseEntity.ok(ComboResponse.builder().build());
    }

    @SuppressWarnings("unused")
    @CircuitBreaker(name = "getComboByName", fallbackMethod = "fallBackByGetComboByName")
    public ResponseEntity<ComboResponse> getComboByName(String name) {
        return getRestClient()
                .get()
                .uri(URI.create("http://localhost:9020/combos/by-name?name=" + name))
                .retrieve()
                .toEntity(ComboResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<ComboResponse> fallBackByGetComboByName(Throwable throwable) {
        return ResponseEntity.ok(ComboResponse.builder().build());
    }
}

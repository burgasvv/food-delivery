package org.burgas.commitservice.handler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.burgas.commitservice.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class RestClientHandler {

    private final RestClient restClient;

    public RestClientHandler(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "getPrincipal", fallbackMethod = "fallBackGetPrincipal")
    public ResponseEntity<IdentityPrincipal> getPrincipal(String authentication) {
        return restClient
                .get()
                .uri("http://localhost:8765/authentication/principal")
                .header(AUTHORIZATION, authentication)
                .retrieve()
                .toEntity(IdentityPrincipal.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<IdentityPrincipal> fallBackGetPrincipal(Throwable throwable) {
        return ResponseEntity.ok(IdentityPrincipal.builder().authenticated(false).build());
    }

    @CircuitBreaker(name = "getFoodById", fallbackMethod = "fallBackGetFoodById")
    public ResponseEntity<FoodResponse> getFoodById(Long foodId) {
        return restClient
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
        return restClient
                .get()
                .uri("http://localhost:9020/food/by-name?name=" + name)
                .retrieve()
                .toEntity(FoodResponse.class);
    }


    @SuppressWarnings("unused")
    private ResponseEntity<FoodResponse> fallBackGetFoodByName(Throwable throwable) {
        return ResponseEntity.ok(FoodResponse.builder().build());
    }

    @CircuitBreaker(name = "getFoodCapacity", fallbackMethod = "fallBackGetFoodCapacity")
    public ResponseEntity<FoodCapacity> getFoodCapacity(Long capacityId, Long foodId) {
        return restClient
                .get()
                .uri("http://localhost:9020/food/food-capacity-ids?capacityId=" + capacityId + "&foodId=" + foodId)
                .retrieve()
                .toEntity(FoodCapacity.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<FoodCapacity> fallBackGetFoodCapacity(Throwable throwable) {
        return ResponseEntity.ok(FoodCapacity.builder().build());
    }

    @CircuitBreaker(name = "getFoodSize", fallbackMethod = "fallBackGetFoodSize")
    public ResponseEntity<FoodSize> getFoodSize(Long size, Long foodId) {
        return restClient
                .get()
                .uri("http://localhost:9020/food/food-size-ids?sizeId=" + size + "&foodId=" + foodId)
                .retrieve()
                .toEntity(FoodSize.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<FoodSize> fallBackGetFoodSize(Throwable throwable) {
        return ResponseEntity.ok(FoodSize.builder().build());
    }

    @CircuitBreaker(name = "getComboById", fallbackMethod = "fallBackByGetComboById")
    public ResponseEntity<ComboResponse> getComboById(Long comboId) {
        return restClient
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
        return restClient
                .get()
                .uri(URI.create("http://localhost:9020/combos/by-name?name=" + name))
                .retrieve()
                .toEntity(ComboResponse.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<ComboResponse> fallBackByGetComboByName(Throwable throwable) {
        return ResponseEntity.ok(ComboResponse.builder().build());
    }

    @CircuitBreaker(name = "getSizeById", fallbackMethod = "fallBackGetSizeById")
    public ResponseEntity<Size> getSizeById(Long sizeId) {
        return restClient
                .get()
                .uri("http://localhost:9020/sizes/by-id?sizeId=" + sizeId)
                .retrieve()
                .toEntity(Size.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Size> fallBackGetSizeById(Throwable throwable) {
        return ResponseEntity.ok(Size.builder().build());
    }

    @CircuitBreaker(name = "getCapacityById", fallbackMethod = "fallBackGetCapacityById")
    public ResponseEntity<Capacity> getCapacityById(Long capacityId) {
        return restClient
                .get()
                .uri("http://localhost:9020/capacities/by-id?capacityId=" + capacityId)
                .retrieve()
                .toEntity(Capacity.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Capacity> fallBackGetCapacityById(Throwable throwable) {
        return ResponseEntity.ok(Capacity.build().build());
    }

    @CircuitBreaker(name = "getCategoryById", fallbackMethod = "fallBackGetCategoryById")
    public ResponseEntity<Category> getCategoryById(Long categoryId) {
        return restClient
                .get()
                .uri("http://localhost:9020/categories/by-id?categoryId=" + categoryId)
                .retrieve()
                .toEntity(Category.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Category> fallBackGetCategoryById(Throwable throwable) {
        return ResponseEntity.ok(Category.builder().build());
    }

    @CircuitBreaker(name = "getIngredientById", fallbackMethod = "fallBackGetIngredientById")
    public ResponseEntity<Ingredient> getIngredientById(Long ingredientId) {
        return restClient
                .get()
                .uri("http://localhost:9020/ingredients/by-id?ingredientId=" + ingredientId)
                .retrieve()
                .toEntity(Ingredient.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Ingredient> fallBackGetIngredientById(Throwable throwable) {
        return ResponseEntity.ok(Ingredient.build().build());
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Token> fallBackCreateToken(Throwable throwable) {
        return ResponseEntity.ok(Token.builder().build());
    }

    @CircuitBreaker(name = "getTokenByValue", fallbackMethod = "fallBackGetTokenByValue")
    public ResponseEntity<Token> getTokenByValue(String value) {
        return restClient
                .get()
                .uri("http://localhost:8080/tokens/by-value?value=" + value)
                .retrieve()
                .toEntity(Token.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Token> fallBackGetTokenByValue(Throwable throwable) {
        return ResponseEntity.ok(Token.builder().build());
    }

    @CircuitBreaker(name = "getTokenById", fallbackMethod = "fallBackGetTokenById")
    public ResponseEntity<Token> getTokenById(Long tokenId) {
        return restClient
                .get()
                .uri("http://localhost:8080/tokens/by-id?tokenId=" + tokenId)
                .retrieve()
                .toEntity(Token.class);
    }

    @SuppressWarnings("unused")
    private ResponseEntity<Token> fallBackGetTokenById(Throwable throwable) {
        return ResponseEntity.ok(Token.builder().build());
    }
}

package org.burgas.foodservice.dto;

import java.util.List;

public record FoodRequest(
        Long id,
        Long categoryId,
        String name,
        String description,
        List<CapacityExchange> capacities,
        List<SizeExchange> sizes,
        List<Long> ingredients,
        Long price
) {
}

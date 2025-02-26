package org.burgas.foodservice.dto;

import java.util.List;

public record ComboRequest(
        Long id,
        Long categoryId,
        String name,
        String description,
        List<ComboFoodExchange> comboFoodExchanges
) {
}

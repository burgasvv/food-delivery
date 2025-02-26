package org.burgas.foodservice.mapper;

import org.burgas.foodservice.dto.ComboRequest;
import org.burgas.foodservice.dto.ComboResponse;
import org.burgas.foodservice.dto.FoodShortResponse;
import org.burgas.foodservice.entity.Category;
import org.burgas.foodservice.entity.Combo;
import org.burgas.foodservice.entity.ComboFood;
import org.burgas.foodservice.entity.Food;
import org.burgas.foodservice.repository.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ComboMapper {

    private final ComboRepository comboRepository;
    private final ComboFoodRepository comboFoodRepository;
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final CapacityRepository capacityRepository;
    private final SizeRepository sizeRepository;

    public ComboMapper(
            ComboRepository comboRepository, ComboFoodRepository comboFoodRepository,
            FoodRepository foodRepository, CategoryRepository categoryRepository,
            CapacityRepository capacityRepository, SizeRepository sizeRepository
    ) {
        this.comboRepository = comboRepository;
        this.comboFoodRepository = comboFoodRepository;
        this.foodRepository = foodRepository;
        this.categoryRepository = categoryRepository;
        this.capacityRepository = capacityRepository;
        this.sizeRepository = sizeRepository;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Combo toCombo(ComboRequest comboRequest) {
        Long comboId = getData(comboRequest.id(), 0L);
        return comboRepository.findById(comboId)
                .map(
                        combo -> {
                            Long finalPrice = getTotalPriceExchange(comboRequest, combo);
                            return Combo.builder()
                                    .id(combo.getId())
                                    .categoryId(getData(comboRequest.categoryId(), combo.getCategoryId()))
                                    .name(getData(comboRequest.name(), combo.getName()))
                                    .description(getData(comboRequest.description(), combo.getDescription()))
                                    .price(getData(finalPrice, combo.getPrice()))
                                    .build();
                        }
                )
                .orElseGet(
                        () -> {
                            Combo newCombo = comboRepository.save(
                                    Combo.builder()
                                            .categoryId(comboRequest.categoryId())
                                            .name(comboRequest.name())
                                            .description(comboRequest.description())
                                            .build()
                            );
                            Long finalPrice = getTotalPriceExchange(comboRequest, newCombo);
                            newCombo.setPrice(finalPrice);
                            return comboRepository.save(newCombo);
                        }
                );
    }

    private Long getTotalPriceExchange(ComboRequest comboRequest, Combo combo) {
        List<Long> prices = new ArrayList<>();

        if (comboRequest.comboFoodExchanges() != null && !comboRequest.comboFoodExchanges().isEmpty()) {
            comboFoodRepository.deleteComboFoodByComboId(combo.getId());

            comboRequest.comboFoodExchanges().forEach(
                    comboFoodExchange -> {
                        Food food = foodRepository.findById(comboFoodExchange.getFoodId()).orElseGet(Food::new);
                        prices.add(food.getPrice() * comboFoodExchange.getAmount());
                        comboFoodRepository.save(
                                ComboFood.builder()
                                        .comboId(combo.getId())
                                        .foodId(food.getId())
                                        .amount(comboFoodExchange.getAmount())
                                        .build()
                        );
                    }
            );
        }
        return prices.stream().reduce(Long::sum).orElse(0L);
    }

    public ComboResponse toComboResponse(Combo combo) {
        return ComboResponse.builder()
                .id(combo.getId())
                .category(categoryRepository.findById(combo.getCategoryId()).orElseGet(Category::new))
                .name(combo.getName())
                .description(combo.getDescription())
                .price(combo.getPrice())
                .food(
                        foodRepository.findFoodsByComboId(combo.getId())
                                .parallelStream()
                                .map(
                                        food -> FoodShortResponse.builder()
                                                .id(food.getId())
                                                .name(food.getName())
                                                .description(food.getDescription())
                                                .category(categoryRepository.findById(food.getCategoryId()).orElseGet(Category::new))
                                                .capacities(capacityRepository.findCapacitiesByFoodId(food.getId()))
                                                .sizes(sizeRepository.findSizesByFoodId(food.getId()))
                                                .amount(
                                                        comboFoodRepository.findComboFoodByComboIdAndFoodId(combo.getId(), food.getId())
                                                                .orElseGet(ComboFood::new).getAmount()
                                                )
                                                .price(food.getPrice())
                                                .build()
                                )
                                .toList()
                )
                .build();
    }
}

package org.burgas.foodservice.mapper;

import org.burgas.foodservice.dto.FoodRequest;
import org.burgas.foodservice.dto.FoodResponse;
import org.burgas.foodservice.entity.*;
import org.burgas.foodservice.repository.*;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final CapacityRepository capacityRepository;
    private final SizeRepository sizeRepository;
    private final IngredientRepository ingredientRepository;
    private final FoodCapacityRepository foodCapacityRepository;
    private final FoodSizeRepository foodSizeRepository;
    private final FoodIngredientRepository foodIngredientRepository;

    public FoodMapper(
            FoodRepository foodRepository, CategoryRepository categoryRepository,
            CapacityRepository capacityRepository, SizeRepository sizeRepository,
            IngredientRepository ingredientRepository, FoodCapacityRepository foodCapacityRepository,
            FoodSizeRepository foodSizeRepository, FoodIngredientRepository foodIngredientRepository
    ) {
        this.foodRepository = foodRepository;
        this.categoryRepository = categoryRepository;
        this.capacityRepository = capacityRepository;
        this.sizeRepository = sizeRepository;
        this.ingredientRepository = ingredientRepository;
        this.foodCapacityRepository = foodCapacityRepository;
        this.foodSizeRepository = foodSizeRepository;
        this.foodIngredientRepository = foodIngredientRepository;
    }

    public <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Food toFood(FoodRequest foodRequest) {
        Long foodId = getData(foodRequest.id(), 0L);
        return foodRepository.findById(foodId)
                .map(
                        food -> {
                            saveFoodExchangeData(foodRequest, food);
                            return Food.builder()
                                    .id(food.getId())
                                    .categoryId(getData(foodRequest.categoryId(), food.getCategoryId()))
                                    .name(getData(foodRequest.name(), food.getName()))
                                    .description(getData(foodRequest.description(), food.getDescription()))
                                    .price(getData(foodRequest.price(), food.getPrice()))
                                    .build();
                        }
                )
                .orElseGet(
                        () -> {
                            Food newFood = foodRepository.save(
                                    Food.builder()
                                            .name(foodRequest.name())
                                            .categoryId(foodRequest.categoryId())
                                            .description(foodRequest.description())
                                            .price(foodRequest.price())
                                            .build()
                            );
                            saveFoodExchangeData(foodRequest, newFood);
                            return newFood;
                        }
                );
    }

    private void saveFoodExchangeData(FoodRequest foodRequest, Food food) {
        if (foodRequest.capacities() != null && !foodRequest.capacities().isEmpty()) {
            foodCapacityRepository.deleteFoodCapacitiesByFoodId(food.getId());
            foodRequest.capacities().forEach(
                    capacityExchange -> {
                        Capacity capacity = capacityRepository.findById(capacityExchange.getId()).orElseGet(Capacity::new);
                        foodCapacityRepository.save(
                                FoodCapacity.builder()
                                        .foodId(food.getId())
                                        .capacityId(capacity.getId())
                                        .price(capacityExchange.getPrice())
                                        .build()
                        );
                    }
            );
        }
        if (foodRequest.sizes() != null && !foodRequest.sizes().isEmpty()) {
            foodSizeRepository.deleteFoodSizesByFoodId(food.getId());
            foodRequest.sizes().forEach(
                    sizeExchange -> {
                        Size size = sizeRepository.findById(sizeExchange.getId()).orElseGet(Size::new);
                        foodSizeRepository.save(
                                FoodSize.builder()
                                        .foodId(food.getId())
                                        .sizeId(size.getId())
                                        .price(sizeExchange.getPrice())
                                        .build()
                        );
                    }
            );
        }
        if (foodRequest.ingredients() != null && !foodRequest.ingredients().isEmpty()) {
            foodIngredientRepository.deleteFoodIngredientsByFoodId(food.getId());
            foodRequest.ingredients().forEach(
                    aLong -> {
                        Ingredient ingredient = ingredientRepository.findById(aLong).orElseGet(Ingredient::new);
                        foodIngredientRepository.save(
                                FoodIngredient.builder()
                                        .foodId(food.getId())
                                        .ingredientId(ingredient.getId())
                                        .build()
                        );
                    }
            );
        }
    }

    public FoodResponse toFoodResponse(Food food) {
        return FoodResponse.builder()
                .id(food.getId())
                .category(categoryRepository.findById(food.getCategoryId()).orElseGet(Category::new))
                .name(food.getName())
                .description(food.getDescription())
                .capacities(capacityRepository.findCapacitiesByFoodId(food.getId()))
                .sizes(sizeRepository.findSizesByFoodId(food.getId()))
                .ingredients(ingredientRepository.findIngredientsByFoodId(food.getId()))
                .price(food.getPrice())
                .build();
    }
}

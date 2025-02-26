package org.burgas.foodservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class FoodIngredientPK {

    private Long foodId;
    private Long ingredientId;

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    @SuppressWarnings("unused")
    public Long getIngredientId() {
        return ingredientId;
    }

    @SuppressWarnings("unused")
    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}

package org.burgas.foodservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(value = FoodIngredientPK.class)
public class FoodIngredient {

    @Id private Long foodId;
    @Id private Long ingredientId;

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final FoodIngredient foodIngredient;

        public Builder() {
            foodIngredient = new FoodIngredient();
        }

        public Builder foodId(Long foodId) {
            this.foodIngredient.foodId = foodId;
            return this;
        }

        public Builder ingredientId(Long ingredientId) {
            this.foodIngredient.ingredientId = ingredientId;
            return this;
        }

        public FoodIngredient build() {
            return foodIngredient;
        }
    }
}

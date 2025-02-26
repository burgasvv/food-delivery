package org.burgas.commitservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(value = ChooseIngredient.class)
public class ChooseIngredient {

    @Id private Long chooseId;
    @Id private Long ingredientId;

    @SuppressWarnings("unused")
    public Long getChooseId() {
        return chooseId;
    }

    @SuppressWarnings("unused")
    public void setChooseId(Long chooseId) {
        this.chooseId = chooseId;
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

        private final ChooseIngredient chooseIngredient;

        public Builder() {
            chooseIngredient = new ChooseIngredient();
        }

        public Builder chooseId(Long chooseId) {
            this.chooseIngredient.chooseId = chooseId;
            return this;
        }

        public Builder ingredientId(Long ingredientId) {
            this.chooseIngredient.ingredientId = ingredientId;
            return this;
        }

        public ChooseIngredient build() {
            return chooseIngredient;
        }
    }
}

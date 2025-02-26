package org.burgas.foodservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(value = ComboFoodPK.class)
public class ComboFood {

    @Id private Long comboId;
    @Id private Long foodId;
    private Long amount;

    @SuppressWarnings("unused")
    public Long getComboId() {
        return comboId;
    }

    @SuppressWarnings("unused")
    public void setComboId(Long comboId) {
        this.comboId = comboId;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    @SuppressWarnings("unused")
    public Long getAmount() {
        return amount;
    }

    @SuppressWarnings("unused")
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ComboFood comboFood;

        public Builder() {
            comboFood = new ComboFood();
        }

        public Builder comboId(Long comboId) {
            this.comboFood.comboId = comboId;
            return this;
        }

        public Builder foodId(Long foodId) {
            this.comboFood.foodId = foodId;
            return this;
        }

        public Builder amount(Long amount) {
            this.comboFood.amount = amount;
            return this;
        }

        public ComboFood build() {
            return comboFood;
        }
    }
}

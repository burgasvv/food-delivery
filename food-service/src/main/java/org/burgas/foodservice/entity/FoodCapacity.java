package org.burgas.foodservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(value = FoodCapacityPK.class)
public class FoodCapacity {

    @Id private Long foodId;
    @Id private Long capacityId;
    private Long price;

    @SuppressWarnings("unused")
    public Long getFoodId() {
        return foodId;
    }

    @SuppressWarnings("unused")
    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    @SuppressWarnings("unused")
    public Long getCapacityId() {
        return capacityId;
    }

    @SuppressWarnings("unused")
    public void setCapacityId(Long capacityId) {
        this.capacityId = capacityId;
    }

    @SuppressWarnings("unused")
    public Long getPrice() {
        return price;
    }

    @SuppressWarnings("unused")
    public void setPrice(Long price) {
        this.price = price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final FoodCapacity foodCapacity;

        public Builder() {
            foodCapacity = new FoodCapacity();
        }

        public Builder foodId(Long foodId) {
            this.foodCapacity.foodId = foodId;
            return this;
        }

        public Builder capacityId(Long capacityId) {
            this.foodCapacity.capacityId = capacityId;
            return this;
        }

        public Builder price(Long price) {
            this.foodCapacity.price = price;
            return this;
        }

        public FoodCapacity build() {
            return foodCapacity;
        }
    }
}

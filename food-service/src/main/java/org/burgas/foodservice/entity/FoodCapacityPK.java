package org.burgas.foodservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class FoodCapacityPK {

    private Long foodId;
    private Long capacityId;

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
}

package org.burgas.foodservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class FoodSizePK {

    private Long foodId;
    private Long sizeId;

    @SuppressWarnings("unused")
    public Long getFoodId() {
        return foodId;
    }

    @SuppressWarnings("unused")
    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    @SuppressWarnings("unused")
    public Long getSizeId() {
        return sizeId;
    }

    @SuppressWarnings("unused")
    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }
}

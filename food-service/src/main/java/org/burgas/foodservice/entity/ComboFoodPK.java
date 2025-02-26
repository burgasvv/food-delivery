package org.burgas.foodservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ComboFoodPK {

    private Long comboId;
    private Long foodId;

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
}

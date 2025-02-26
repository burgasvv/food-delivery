package org.burgas.commitservice.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ChooseIngredientPK {

    private Long chooseId;
    private Long ingredientId;

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
}

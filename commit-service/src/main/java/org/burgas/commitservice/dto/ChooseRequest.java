package org.burgas.commitservice.dto;

import java.util.List;

public class ChooseRequest {

    private Long id;
    private Long foodId;
    private Long comboId;
    private Long capacityId;
    private Long sizeId;
    private Long amount;
    private Boolean food;
    private List<Long> ingredients;
    private Long commitId;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Long getFoodId() {
        return foodId;
    }

    @SuppressWarnings("unused")
    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    @SuppressWarnings("unused")
    public Long getComboId() {
        return comboId;
    }

    @SuppressWarnings("unused")
    public void setComboId(Long comboId) {
        this.comboId = comboId;
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
    public Long getSizeId() {
        return sizeId;
    }

    @SuppressWarnings("unused")
    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    @SuppressWarnings("unused")
    public Long getAmount() {
        return amount;
    }

    @SuppressWarnings("unused")
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Boolean getFood() {
        return food;
    }

    @SuppressWarnings("unused")
    public void setFood(Boolean food) {
        this.food = food;
    }

    @SuppressWarnings("unused")
    public List<Long> getIngredients() {
        return ingredients;
    }

    @SuppressWarnings("unused")
    public void setIngredients(List<Long> ingredients) {
        this.ingredients = ingredients;
    }

    @SuppressWarnings("unused")
    public Long getCommitId() {
        return commitId;
    }

    @SuppressWarnings("unused")
    public void setCommitId(Long commitId) {
        this.commitId = commitId;
    }
}

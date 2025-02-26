package org.burgas.commitservice.dto;

import org.burgas.foodservice.entity.Capacity;
import org.burgas.foodservice.entity.Category;
import org.burgas.foodservice.entity.Ingredient;
import org.burgas.foodservice.entity.Size;

import java.util.List;

public class ChooseResponse {

    private Long id;
    private Category category;
    private String name;
    private String description;
    private Size size;
    private Capacity capacity;
    private Long amount;
    private Long price;
    private Boolean isFood;
    private List<Ingredient> ingredients;
    private Long commitId;
    private Long mediaId;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Category getCategory() {
        return category;
    }

    @SuppressWarnings("unused")
    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("unused")
    public Size getSize() {
        return size;
    }

    @SuppressWarnings("unused")
    public void setSize(Size size) {
        this.size = size;
    }

    @SuppressWarnings("unused")
    public Capacity getCapacity() {
        return capacity;
    }

    @SuppressWarnings("unused")
    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    @SuppressWarnings("unused")
    public Long getAmount() {
        return amount;
    }

    @SuppressWarnings("unused")
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @SuppressWarnings("unused")
    public Long getPrice() {
        return price;
    }

    @SuppressWarnings("unused")
    public void setPrice(Long price) {
        this.price = price;
    }

    @SuppressWarnings("unused")
    public Boolean getFood() {
        return isFood;
    }

    @SuppressWarnings("unused")
    public void setFood(Boolean food) {
        isFood = food;
    }

    @SuppressWarnings("unused")
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @SuppressWarnings("unused")
    public void setIngredients(List<Ingredient> ingredients) {
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

    @SuppressWarnings("unused")
    public Long getMediaId() {
        return mediaId;
    }

    @SuppressWarnings("unused")
    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ChooseResponse chooseResponse;

        public Builder() {
            chooseResponse = new ChooseResponse();
        }

        public Builder id(Long id) {
            this.chooseResponse.id = id;
            return this;
        }

        public Builder category(Category category) {
            this.chooseResponse.category = category;
            return this;
        }

        public Builder name(String name) {
            this.chooseResponse.name = name;
            return this;
        }

        public Builder description(String description) {
            this.chooseResponse.description = description;
            return this;
        }

        public Builder size(Size size) {
            this.chooseResponse.size = size;
            return this;
        }

        public Builder capacity(Capacity capacity) {
            this.chooseResponse.capacity = capacity;
            return this;
        }

        public Builder amount(Long amount) {
            this.chooseResponse.amount = amount;
            return this;
        }

        public Builder price(Long price) {
            this.chooseResponse.price = price;
            return this;
        }

        public Builder isFood(Boolean isFood) {
            this.chooseResponse.isFood = isFood;
            return this;
        }

        public Builder ingredients(List<Ingredient> ingredients) {
            this.chooseResponse.ingredients = ingredients;
            return this;
        }

        public Builder commitId(Long commitId) {
            this.chooseResponse.commitId = commitId;
            return this;
        }

        public Builder mediaId(Long mediaId) {
            this.chooseResponse.mediaId = mediaId;
            return this;
        }

        public ChooseResponse build() {
            return chooseResponse;
        }
    }
}

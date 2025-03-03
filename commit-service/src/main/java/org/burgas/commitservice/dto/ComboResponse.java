package org.burgas.commitservice.dto;

import java.util.List;

public class ComboResponse {

    private Long id;
    private Category category;
    private String name;
    private String description;
    private Long price;
    private Long amount;
    private List<FoodShortResponse> food;
    private Long mediaId;

    public Long getId() {
        return id;
    }

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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
    public List<FoodShortResponse> getFood() {
        return food;
    }

    @SuppressWarnings("unused")
    public void setFood(List<FoodShortResponse> food) {
        this.food = food;
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

        private final ComboResponse comboResponse;

        public Builder() {
            comboResponse = new ComboResponse();
        }

        public Builder id(Long id) {
            this.comboResponse.id = id;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder category(Category category) {
            this.comboResponse.category = category;
            return this;
        }

        public Builder name(String name) {
            this.comboResponse.name = name;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder description(String description) {
            this.comboResponse.description = description;
            return this;
        }

        public Builder price(Long price) {
            this.comboResponse.price = price;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder amount(Long amount) {
            this.comboResponse.amount = amount;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder food(List<FoodShortResponse> food) {
            this.comboResponse.food = food;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder mediaId(Long mediaId) {
            this.comboResponse.mediaId = mediaId;
            return this;
        }

        public ComboResponse build() {
            return comboResponse;
        }
    }
}

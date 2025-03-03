package org.burgas.commitservice.dto;

import java.util.List;

public class FoodShortResponse {

    private Long id;
    private Category category;
    private String name;
    private String description;
    private List<Capacity> capacities;
    private List<Size> sizes;
    private Long price;
    private Long amount;

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

    @SuppressWarnings("unused")
    public List<Capacity> getCapacities() {
        return capacities;
    }

    @SuppressWarnings("unused")
    public void setCapacities(List<Capacity> capacities) {
        this.capacities = capacities;
    }

    @SuppressWarnings("unused")
    public List<Size> getSizes() {
        return sizes;
    }

    @SuppressWarnings("unused")
    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final FoodShortResponse foodShortResponse;

        public Builder() {
            foodShortResponse = new FoodShortResponse();
        }

        public Builder id(Long id) {
            this.foodShortResponse.id = id;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder category(Category category) {
            this.foodShortResponse.category = category;
            return this;
        }

        public Builder name(String name) {
            this.foodShortResponse.name = name;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder description(String description) {
            this.foodShortResponse.description = description;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder capacities(List<Capacity> capacities) {
            this.foodShortResponse.capacities = capacities;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder sizes(List<Size> sizes) {
            this.foodShortResponse.sizes = sizes;
            return this;
        }

        public Builder price(Long price) {
            this.foodShortResponse.price = price;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder amount(Long amount) {
            this.foodShortResponse.amount = amount;
            return this;
        }

        public FoodShortResponse build() {
            return foodShortResponse;
        }
    }
}

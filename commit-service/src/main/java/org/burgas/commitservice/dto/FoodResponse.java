package org.burgas.commitservice.dto;

import java.util.List;

public class FoodResponse {

    private Long id;
    private Category category;
    private String name;
    private String description;
    private List<Capacity> capacities;
    private List<Size> sizes;
    private List<Ingredient> ingredients;
    private Long price;
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
    public void setCategory() {
        setCategory(null);
    }

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

    @SuppressWarnings("unused")
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @SuppressWarnings("unused")
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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
    public Long getMediaId() {
        return mediaId;
    }

    @SuppressWarnings("unused")
    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "FoodResponse{" +
               "id=" + id +
               ", category=" + category +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", capacities=" + capacities +
               ", sizes=" + sizes +
               ", ingredients=" + ingredients +
               ", price=" + price +
               ", mediaId=" + mediaId +
               '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final FoodResponse foodResponse;

        public Builder() {
            foodResponse = new FoodResponse();
        }

        public Builder id(Long id) {
            this.foodResponse.id = id;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder category(Category category) {
            this.foodResponse.category = category;
            return this;
        }

        public Builder name(String name) {
            this.foodResponse.name = name;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder description(String description) {
            this.foodResponse.description = description;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder capacities(List<Capacity> capacities) {
            this.foodResponse.capacities = capacities;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder sizes(List<Size> sizes) {
            this.foodResponse.sizes = sizes;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder ingredients(List<Ingredient> ingredients) {
            this.foodResponse.ingredients = ingredients;
            return this;
        }

        public Builder price(Long price) {
            this.foodResponse.price = price;
            return this;
        }

        @SuppressWarnings("unused")
        public Builder mediaId(Long mediaId) {
            this.foodResponse.mediaId = mediaId;
            return this;
        }

        public FoodResponse build() {
            return foodResponse;
        }
    }
}

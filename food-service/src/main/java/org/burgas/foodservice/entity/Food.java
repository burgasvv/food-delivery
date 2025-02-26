package org.burgas.foodservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long categoryId;
    private String name;
    private String description;
    private Long price;
    private Long mediaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Long getCategoryId() {
        return categoryId;
    }

    @SuppressWarnings("unused")
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Food food;

        public Builder() {
            food = new Food();
        }

        public Builder id(Long id) {
            this.food.id = id;
            return this;
        }

        public Builder categoryId(Long categoryId) {
            this.food.categoryId = categoryId;
            return this;
        }

        public Builder name(String name) {
            this.food.name = name;
            return this;
        }

        public Builder description(String description) {
            this.food.description = description;
            return this;
        }

        public Builder price(Long price) {
            this.food.price = price;
            return this;
        }

        public Builder mediaId(Long mediaId) {
            this.food.mediaId = mediaId;
            return this;
        }

        public Food build() {
            return food;
        }
    }
}

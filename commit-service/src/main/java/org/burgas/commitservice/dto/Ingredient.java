package org.burgas.commitservice.dto;

public class Ingredient {

    private Long id;
    private String name;
    private Long price;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    public Long getPrice() {
        return price;
    }

    @SuppressWarnings("unused")
    public void setPrice(Long price) {
        this.price = price;
    }

    public static Builder build() {
        return new Builder();
    }

    public static class Builder {

        private final Ingredient ingredient;

        public Builder() {
            ingredient = new Ingredient();
        }

        public Builder id(Long id) {
            this.ingredient.id = id;
            return this;
        }

        public Builder name(String name) {
            this.ingredient.name = name;
            return this;
        }

        public Builder price(Long price) {
            this.ingredient.price = price;
            return this;
        }

        public Ingredient build() {
            return ingredient;
        }
    }
}

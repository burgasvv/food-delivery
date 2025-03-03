package org.burgas.commitservice.dto;

public class Category {

    private Long id;
    private String name;

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Category category;

        public Builder() {
            category = new Category();
        }

        public Builder id(Long id) {
            this.category.id = id;
            return this;
        }

        public Builder name(String name) {
            this.category.name = name;
            return this;
        }

        public Category build() {
            return category;
        }
    }
}

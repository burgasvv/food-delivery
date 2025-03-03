package org.burgas.commitservice.dto;

public class FoodSize {

    private Long foodId;
    private Long sizeId;
    private Long price;

    @SuppressWarnings("unused")
    public Long getFoodId() {
        return foodId;
    }

    @SuppressWarnings("unused")
    public void setFoodId(Long foodId) {
        this.foodId = foodId;
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
    public Long getPrice() {
        return price;
    }

    @SuppressWarnings("unused")
    public void setPrice(Long price) {
        this.price = price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final FoodSize foodSize;

        public Builder() {
            foodSize = new FoodSize();
        }

        public Builder foodId(Long foodId) {
            this.foodSize.foodId = foodId;
            return this;
        }

        public Builder sizeId(Long sizeId) {
            this.foodSize.sizeId = sizeId;
            return this;
        }

        public Builder price(Long price) {
            this.foodSize.price = price;
            return this;
        }

        public FoodSize build() {
            return foodSize;
        }
    }
}

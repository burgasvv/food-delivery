package org.burgas.foodservice.dto;

public class ComboFoodExchange {

    private Long foodId;
    private Long amount;

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
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

        private final ComboFoodExchange comboFoodExchange;

        public Builder() {
            comboFoodExchange = new ComboFoodExchange();
        }

        public Builder foodId(Long foodId) {
            this.comboFoodExchange.foodId = foodId;
            return this;
        }

        public Builder amount(Long amount) {
            this.comboFoodExchange.amount = amount;
            return this;
        }

        public ComboFoodExchange build() {
            return comboFoodExchange;
        }
    }
}

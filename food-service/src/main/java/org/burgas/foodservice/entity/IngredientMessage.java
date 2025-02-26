package org.burgas.foodservice.entity;

public enum IngredientMessage {

    INGREDIENT_DELETED("Ингредиент по идентификатору %s успешно удален"),
    INGREDIENT_NOT_FOUND("Ингредиент по идентификатору %s не найден");

    private final String message;

    IngredientMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

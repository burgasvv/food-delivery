package org.burgas.foodservice.entity;

public enum FoodMessage {

    FOOD_DELETED("Блюдо по идентификатору %s успешно удалено"),
    FOOD_NOT_FOUND("Блюдо по идентификатору %s не найдено"),
    IMAGE_FOR_FOOD_UPLOADED("Изображение для блюда успешно загружено"),
    IMAGE_FOR_FOOD_DELETED("Изображение блюда успешно удалено"),
    PREVIEW_FOOD_IMAGE_DELETED("Preview food image deleted");

    private final String message;

    FoodMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

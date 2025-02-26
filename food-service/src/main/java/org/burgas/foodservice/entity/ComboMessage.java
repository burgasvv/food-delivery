package org.burgas.foodservice.entity;

public enum ComboMessage {

    COMBO_DELETED("Комбо из блюд с идентификатором %s успешно удалено"),
    COMBO_NOT_FOUND("Комбо из блюд с идентификатором %s не найдено"),
    IMAGE_FOR_COMBO_UPLOADED("Изображение для комбо успешно загружено"),
    IMAGE_FOR_COMBO_DELETED("Изображение комбо успешно удалено"),
    PREVIEW_COMBO_IMAGE_DELETED("Preview combo image deleted");

    private final String message;

    ComboMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

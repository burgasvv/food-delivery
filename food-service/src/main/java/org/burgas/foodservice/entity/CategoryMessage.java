package org.burgas.foodservice.entity;

public enum CategoryMessage {

    CATEGORY_DELETED("Категория с идентификатором %s успешно удалена"),
    CATEGORY_NOT_FOUND("Категория с идентификатором %s не найдена");

    private final String message;

    CategoryMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

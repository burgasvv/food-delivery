package org.burgas.foodservice.entity;

public enum SizeMessage {

    SIZE_DELETED("Размер по идентификатору %s успешно удален"),
    SIZE_NOT_FOUND("Размер по идентификатору %s не найден");

    private final String message;

    SizeMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package org.burgas.foodservice.entity;

public enum CapacityMessage {

    CAPACITY_DELETED("Вместимость по идентификатору %s успешно удалена"),
    CAPACITY_NOT_FOUND("Вместимость по идентификатору %s не найдена");

    private final String message;

    CapacityMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

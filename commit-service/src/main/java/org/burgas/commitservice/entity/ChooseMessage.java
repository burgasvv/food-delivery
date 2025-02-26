package org.burgas.commitservice.entity;

public enum ChooseMessage {

    CHOOSE_INCREMENTED("Количество товара в заказе увеличено на 1"),
    CHOOSE_DECREMENTED("Количество товара в заказе уменьшено на 1"),
    CHOOSE_NOT_FOUND("Указанный вами товар не найден в заказе"),
    CHOOSELESS("Количество продуктов в заказе не может быть меньше одного"),
    COOKIE_NOT_FOUND("Куки браузера не найден");

    private final String message;

    ChooseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package org.burgas.commitservice.entity;

public enum CommitMessage {

    COMMIT_CLOSED_BY_TOKEN("Заказ закрыт по коду заказа"),
    COMMIT_CLOSED_BY_USER_ID("Заказ закрыт по идентификатору пользователя"),
    NO_USER_COMMITS("Пользовательские заказы отсутствуют"),
    COMMIT_IS_NOT_CLOSED("Заказ не может быть закрыт"),
    TOKEN_NOT_FOUND("Токен заказа не найден"),
    COMMIT_ALREADY_CLOSED("Данный заказ уже закрыт"),
    NOT_AUTHORIZED("Пользователь не авторизован, заказы отсутствуют"),
    COMMIT_DELETED_BY_SESSION("Заказ успешно удален через сессию"),
    COMMIT_DELETED_BY_REPOSITORY("Заказ успешно удален через экземпляр репозитория"),
    COMMIT_NOT_FOUND("Заказ по указанному вами товару не найден");

    private final String message;

    CommitMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

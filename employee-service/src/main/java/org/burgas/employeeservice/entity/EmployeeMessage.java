package org.burgas.employeeservice.entity;

public enum EmployeeMessage {

    NOT_AUTHORIZED_AND_NOT_AUTHENTICATED("Пользователь не аутентифицирован и не авторизован"),
    NO_IDENTITY_ID("Идентификатор пользователя не указан при создании аккаунта работника"),
    EMPLOYEE_DELETED("Аккаунт сотрудника с идентификатором %s успешно удален"),
    EMPLOYEE_NOT_FOUND("Сотрудник с идентификатором %s не найден"),
    EMPLOYEE_IMAGE_SAVED("Изображение сотрудника успешно сохранено"),
    EMPLOYEE_IMAGE_DELETED("Изображение пользователя успешно удалено");

    private final String message;

    EmployeeMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

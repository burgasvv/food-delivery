package org.burgas.departmentservice.util;

import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {

    public static final String ADDRESS_DELETED = "Адрес с идентификатором %s успешно удален";
    public static final String ADDRESS_NOT_FOUND = "Адрес с идентификатором %s не найден";

    public static final String DEPARTMENT_DELETED = "Отдел с идентификатором %s успешно удален";
    public static final String DEPARTMENT_NOT_FOUND = "Отдел с идентификатором %s не найден";

    public static final String NOT_AUTHORIZED = "Пользователь не авторизован";
    public static final String NOT_AUTHENTICATED = "Пользователь не аутентифицирован";
    public static final String AUTHORITY_NOT_FOUND = "Роль с идентификатором %s не найдена";
    public static final String AUTHORITY_DELETED = "Роль с идентификатором %s успешно удалена";
    public static final String ADMIN_AUTHORITY_CAN_NOT_BE_CREATED = "Невозможно создать аккаунт администратора";

    public static final String IDENTITY_DELETED = "Пользователь с идентификатором %s успешно удален";
    public static final String IDENTITY_NOT_FOUND = "Пользователь с идентификатором %s не найден";
    public static final String IDENTITY_WAS_BANNED = "Пользователю с идентификатором %s выдан бан";
    public static final String IDENTITY_WAS_UNBANNED = "Пользователь с идентификатором %s был выдан разбан";
}

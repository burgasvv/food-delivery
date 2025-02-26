package org.burgas.departmentservice.util;

import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {

    public static final String ADDRESS_DELETED = "Адрес с идентификатором %s успешно удален";
    public static final String ADDRESS_NOT_FOUND = "Адрес с идентификатором %s не найден";

    public static final String DEPARTMENT_DELETED = "Отдел с идентификатором %s успешно удален";
    public static final String DEPARTMENT_NOT_FOUND = "Отдел с идентификатором %s не найден";
}

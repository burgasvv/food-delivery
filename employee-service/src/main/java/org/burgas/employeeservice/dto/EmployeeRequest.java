package org.burgas.employeeservice.dto;

public record EmployeeRequest(
        Long id,
        String firstname,
        String lastname,
        String patronymic,
        Long departmentId,
        Long identityId
) {
}

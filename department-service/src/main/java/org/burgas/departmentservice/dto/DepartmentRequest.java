package org.burgas.departmentservice.dto;

import java.time.LocalTime;

public record DepartmentRequest(
        Long id,
        Long addressId,
        LocalTime opensAt,
        LocalTime closesAt,
        Boolean opened
) {
}

package org.burgas.departmentservice.dto;

public record AddressRequest(
        Long id,
        String city,
        String street,
        String house,
        String apartment
) {
}

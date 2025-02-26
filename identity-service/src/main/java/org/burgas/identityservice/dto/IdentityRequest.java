package org.burgas.identityservice.dto;

import java.time.LocalDateTime;

public record IdentityRequest(
        Long id,
        String username,
        String password,
        String email,
        LocalDateTime registeredAt,
        Long authorityId,
        Boolean enabled
) {
}

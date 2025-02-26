package org.burgas.gatewayserver.mapper;

import org.burgas.gatewayserver.dto.AuthorityResponse;
import org.burgas.gatewayserver.entity.Authority;
import org.springframework.stereotype.Component;

@Component
public class AuthorityMapper {

    public AuthorityResponse toAuthorityResponse(Authority authority) {
        return AuthorityResponse.builder()
                .id(authority.getId())
                .name(authority.getName())
                .build();
    }
}

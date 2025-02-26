package org.burgas.gatewayserver.mapper;

import org.burgas.gatewayserver.dto.IdentityPrincipal;
import org.burgas.gatewayserver.dto.IdentityResponse;
import org.springframework.stereotype.Component;

@Component
public class IdentityPrincipalMapper {

    public IdentityPrincipal toIdentityPrincipal(IdentityResponse identityResponse, Boolean authenticated) {
        return IdentityPrincipal.builder()
                .id(identityResponse.getId())
                .username(identityResponse.getUsername())
                .password(identityResponse.getPassword())
                .email(identityResponse.getEmail())
                .authority(identityResponse.getAuthority().getName())
                .authenticated(authenticated)
                .build();
    }
}

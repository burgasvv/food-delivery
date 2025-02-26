package org.burgas.gatewayserver.mapper;

import org.burgas.gatewayserver.dto.AuthorityResponse;
import org.burgas.gatewayserver.dto.IdentityResponse;
import org.burgas.gatewayserver.entity.Identity;
import org.burgas.gatewayserver.repository.AuthorityRepository;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class IdentityMapper {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    public IdentityMapper(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper
    ) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }

    public IdentityResponse toIdentityResponse(Identity identity) {
        return IdentityResponse.builder()
                .id(identity.getId())
                .username(identity.getUsername())
                .password(identity.getPassword())
                .email(identity.getEmail())
                .authority(
                        authorityRepository.findById(identity.getAuthorityId())
                                .map(authorityMapper::toAuthorityResponse)
                                .orElseGet(AuthorityResponse::new)
                )
                .registeredAt(
                        identity.getRegisteredAt().format(
                                DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm")
                        )
                )
                .enabled(identity.getEnabled())
                .build();
    }
}

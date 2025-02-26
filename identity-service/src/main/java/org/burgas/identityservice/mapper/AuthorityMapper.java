package org.burgas.identityservice.mapper;

import org.burgas.identityservice.dto.AuthorityRequest;
import org.burgas.identityservice.dto.AuthorityResponse;
import org.burgas.identityservice.entity.Authority;
import org.burgas.identityservice.repository.AuthorityRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthorityMapper {

    private final AuthorityRepository authorityRepository;

    public AuthorityMapper(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Authority toAuthority(AuthorityRequest authorityRequest) {
        Long authorityId = getData(authorityRequest.id(), 0L);
        return authorityRepository.findById(authorityId)
                .map(
                        authority -> Authority.builder()
                                .id(authority.getId())
                                .name(getData(authorityRequest.name(), authority.getName()))
                                .build()
                )
                .orElseGet(
                        () -> Authority.builder()
                                .name(authorityRequest.name())
                                .build()
                );
    }

    public AuthorityResponse toAuthorityResponse(Authority authority) {
        return AuthorityResponse.builder()
                .id(authority.getId())
                .name(authority.getName())
                .build();
    }
}

package org.burgas.identityservice.mapper;

import org.burgas.identityservice.dto.AuthorityResponse;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.entity.Identity;
import org.burgas.identityservice.exception.AuthorityCanNotBeCreatedException;
import org.burgas.identityservice.exception.AuthorityNotFoundException;
import org.burgas.identityservice.repository.AuthorityRepository;
import org.burgas.identityservice.repository.IdentityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.burgas.identityservice.util.ServiceUtil.ADMIN_AUTHORITY_CAN_NOT_BE_CREATED;
import static org.burgas.identityservice.util.ServiceUtil.AUTHORITY_NOT_FOUND;

@Component
public class IdentityMapper {

    private final IdentityRepository identityRepository;
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;
    private final PasswordEncoder passwordEncoder;

    public IdentityMapper(
            IdentityRepository identityRepository, AuthorityRepository authorityRepository,
            AuthorityMapper authorityMapper, PasswordEncoder passwordEncoder
    ) {
        this.identityRepository = identityRepository;
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private <T> T getData(T first, T second) {
        return first == null ? second : first;
    }

    public Identity toIdentity(IdentityRequest identityRequest) {
        Long identityId = getData(identityRequest.id(), 0L);
        String authority = identityRepository.findAuthorityNameByIdentityId(identityId);
        Long authorityId = identityRepository.findAuthorityIdByIdentityId(identityId);

        if ("ROLE_ADMIN".equals(authority))
            if (getData(identityRequest.authorityId(), authorityId) > 0 &&
                    getData(identityRequest.authorityId(), authorityId) <= 3)
                return getIdentity(identityRequest, identityId);

            else
                throw new AuthorityNotFoundException(
                        String.format(AUTHORITY_NOT_FOUND, identityRequest.authorityId())
                );

        else
            if (getData(identityRequest.authorityId(), authorityId) > 1 &&
                    getData(identityRequest.authorityId(), authorityId) <= 3)
                return getIdentity(identityRequest, identityId);

            else
                throw new AuthorityCanNotBeCreatedException(ADMIN_AUTHORITY_CAN_NOT_BE_CREATED);
    }

    private Identity getIdentity(IdentityRequest identityRequest, Long identityId) {
        return identityRepository.findById(identityId)
                .map(
                        identity -> Identity.builder()
                                .id(identity.getId())
                                .username(getData(identityRequest.username(), identity.getUsername()))
                                .password(getData(passwordEncoder.encode(identityRequest.password()), identity.getPassword()))
                                .email(getData(identityRequest.email(), identity.getEmail()))
                                .authorityId(getData(identityRequest.authorityId(), identity.getAuthorityId()))
                                .registeredAt(getData(identityRequest.registeredAt(), identity.getRegisteredAt()))
                                .enabled(getData(identityRequest.enabled(), identity.getEnabled()))
                                .build()
                )
                .orElseGet(
                        () -> Identity.builder()
                                .username(identityRequest.username())
                                .password(passwordEncoder.encode(identityRequest.password()))
                                .email(identityRequest.email())
                                .authorityId(identityRequest.authorityId())
                                .registeredAt(LocalDateTime.now())
                                .enabled(true)
                                .build()
                );
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

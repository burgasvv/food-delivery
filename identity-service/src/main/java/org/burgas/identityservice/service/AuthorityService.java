package org.burgas.identityservice.service;

import org.burgas.identityservice.dto.AuthorityRequest;
import org.burgas.identityservice.dto.AuthorityResponse;
import org.burgas.identityservice.exception.AuthorityNotFoundException;
import org.burgas.identityservice.mapper.AuthorityMapper;
import org.burgas.identityservice.repository.AuthorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.burgas.identityservice.util.ServiceUtil.*;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    public AuthorityService(
            AuthorityRepository authorityRepository, AuthorityMapper authorityMapper
    ) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }

    public List<AuthorityResponse> findAll() {
        return authorityRepository.findAll()
                .stream()
                .map(authorityMapper::toAuthorityResponse)
                .toList();
    }

    public AuthorityResponse findById(Long authorityId) {
        return authorityRepository.findById(authorityId)
                .map(authorityMapper::toAuthorityResponse)
                .orElseGet(AuthorityResponse::new);
    }

    public AuthorityResponse findByName(String name) {
        return authorityRepository.findAuthorityByName(name)
                .map(authorityMapper::toAuthorityResponse)
                .orElseGet(AuthorityResponse::new);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public Long createOrUpdate(AuthorityRequest authorityRequest) {
        return authorityRepository.save(authorityMapper.toAuthority(authorityRequest)).getId();
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long authorityId) {
        return authorityRepository.findById(authorityId)
                .map(
                        authority -> {
                            authorityRepository.deleteById(authority.getId());
                            return AUTHORITY_DELETED;
                        }
                )
                .orElseThrow(
                        () -> new AuthorityNotFoundException(String.format(AUTHORITY_NOT_FOUND, authorityId))
                );
    }
}

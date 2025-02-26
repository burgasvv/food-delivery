package org.burgas.identityservice.service;

import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.exception.IdentityNotFoundException;
import org.burgas.identityservice.exception.NotAuthenticatedException;
import org.burgas.identityservice.handler.RestClientHandler;
import org.burgas.identityservice.mapper.IdentityMapper;
import org.burgas.identityservice.repository.IdentityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.burgas.identityservice.util.ServiceUtil.*;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class IdentityService {

    private final IdentityRepository identityRepository;
    private final IdentityMapper identityMapper;
    private final RestClientHandler restClientHandler;

    public IdentityService(
            IdentityRepository identityRepository,
            IdentityMapper identityMapper, RestClientHandler restClientHandler
    ) {
        this.identityRepository = identityRepository;
        this.identityMapper = identityMapper;
        this.restClientHandler = restClientHandler;
    }

    public List<IdentityResponse> findAll() {
        return identityRepository.findAll()
                .stream()
                .map(identityMapper::toIdentityResponse)
                .toList();
    }

    public IdentityResponse findById(Long identityId) {
        return identityRepository.findById(identityId)
                .map(identityMapper::toIdentityResponse)
                .orElseGet(IdentityResponse::new);
    }

    public IdentityResponse findByEmail(String email) {
        return identityRepository.findIdentityByEmail(email)
                .map(identityMapper::toIdentityResponse)
                .orElseGet(IdentityResponse::new);
    }

    public IdentityResponse findByUsername(String username) {
        return identityRepository.findIdentityByUsername(username)
                .map(identityMapper::toIdentityResponse)
                .orElseGet(IdentityResponse::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public IdentityResponse createOrUpdate(IdentityRequest identityRequest, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getIdentityPrincipal(authentication).getBody();
        if (
                Objects.requireNonNull(identityPrincipal).getAuthenticated() &&
                identityPrincipal.getId().equals(identityRequest.id())
        )
            return createOrUpdateIdentityResponse(identityRequest);

        else if (identityRequest.id() == null)
            return createOrUpdateIdentityResponse(identityRequest);

        else throw new NotAuthenticatedException(NOT_AUTHENTICATED);
    }

    private IdentityResponse createOrUpdateIdentityResponse(IdentityRequest identityRequest) {
        return identityMapper.toIdentityResponse(
                identityRepository.save(identityMapper.toIdentity(identityRequest))
        );
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteById(Long identityId, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getIdentityPrincipal(authentication).getBody();
        if (
                Objects.requireNonNull(identityPrincipal).getAuthenticated() &&
                identityPrincipal.getId().equals(identityId)
        ) {
            return identityRepository.findById(identityId)
                    .map(
                            identity -> {
                                identityRepository.deleteById(identity.getId());
                                return String.format(IDENTITY_DELETED, identity.getId());
                            }
                    )
                    .orElseThrow(() -> new IdentityNotFoundException(String.format(IDENTITY_NOT_FOUND, identityId)));
        } else
            throw new NotAuthenticatedException(NOT_AUTHENTICATED);
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String addBan(Long identityId) {
        return identityRepository.findById(identityId)
                .map(
                        identity -> {
                            identity.setEnabled(false);
                            identityRepository.save(identity);
                            return String.format(IDENTITY_WAS_BANNED, identityId);
                        }
                )
                .orElseThrow(() -> new IdentityNotFoundException(String.format(IDENTITY_NOT_FOUND, identityId)));
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String unban(Long identityId) {
        return identityRepository.findById(identityId)
                .map(
                        identity -> {
                            identity.setEnabled(true);
                            identityRepository.save(identity);
                            return String.format(IDENTITY_WAS_UNBANNED, identityId);
                        }
                )
                .orElseThrow(() -> new IdentityNotFoundException(String.format(IDENTITY_NOT_FOUND, identityId)));
    }
}

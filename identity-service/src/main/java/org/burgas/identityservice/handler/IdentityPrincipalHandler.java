package org.burgas.identityservice.handler;

import org.burgas.identityservice.dto.IdentityPrincipal;
import org.burgas.identityservice.exception.NotAuthenticatedException;
import org.burgas.identityservice.exception.NotAuthorizedException;
import org.springframework.stereotype.Component;

import static org.burgas.identityservice.util.ServiceUtil.NOT_AUTHENTICATED;
import static org.burgas.identityservice.util.ServiceUtil.NOT_AUTHORIZED;

@Component
public class IdentityPrincipalHandler {

    private final RestClientHandler restClientHandler;

    public IdentityPrincipalHandler(RestClientHandler restClientHandler) {
        this.restClientHandler = restClientHandler;
    }

    public <T> T handleAdminIdentityPrincipal(T handleData, String authentication) {
        IdentityPrincipal identityPrincipal = restClientHandler.getIdentityPrincipal(authentication).getBody();
        if (identityPrincipal != null && identityPrincipal.getAuthenticated()) {

            if ("ROLE_ADMIN".equals(identityPrincipal.getAuthority())) {
                return handleData;

            } else
                throw new NotAuthorizedException(NOT_AUTHORIZED);

        } else
            throw new NotAuthenticatedException(NOT_AUTHENTICATED);
    }
}

package org.burgas.departmentservice.handler;

import org.burgas.identityservice.handler.IdentityPrincipalHandler;
import org.burgas.identityservice.handler.RestClientHandler;
import org.springframework.stereotype.Component;

@Component
public class IdentityPrincipalHandlerDepartmentService extends IdentityPrincipalHandler {

    public IdentityPrincipalHandlerDepartmentService(RestClientHandler restClientHandler) {
        super(restClientHandler);
    }
}

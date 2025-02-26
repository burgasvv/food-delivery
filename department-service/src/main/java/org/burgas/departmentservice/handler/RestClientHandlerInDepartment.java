package org.burgas.departmentservice.handler;

import org.burgas.identityservice.handler.RestClientHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientHandlerInDepartment extends RestClientHandler {

    public RestClientHandlerInDepartment(RestClient restClient) {
        super(restClient);
    }
}

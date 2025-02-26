package org.burgas.gatewayserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.gatewayserver.dto.IdentityPrincipal;
import org.burgas.gatewayserver.dto.IdentityResponse;
import org.burgas.gatewayserver.mapper.IdentityPrincipalMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/authentication")
@Tag(
        name = "AuthenticationController",
        description = "Контроллер для получения данных авторизации пользователя"
)
public class AuthenticationController {

    private final IdentityPrincipalMapper identityPrincipalMapper;

    public AuthenticationController(IdentityPrincipalMapper identityPrincipalMapper) {
        this.identityPrincipalMapper = identityPrincipalMapper;
    }

    @GetMapping("/principal")
    @Operation(
            summary = "Данные авторизации пользователя",
            description = "Получения объекта авторизации пользователя (авторизован или не авторизован)"
    )
    public @ResponseBody ResponseEntity<IdentityPrincipal> getIdentityPrincipal(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(
                    identityPrincipalMapper.toIdentityPrincipal(
                            (IdentityResponse) authentication.getPrincipal(), true
                    )
            );
        } else {
            return ResponseEntity.ok(
                    IdentityPrincipal.builder().username("anonymous").authenticated(false).build()
            );
        }
    }
}

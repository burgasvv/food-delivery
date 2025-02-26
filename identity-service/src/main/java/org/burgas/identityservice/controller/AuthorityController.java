package org.burgas.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.identityservice.dto.AuthorityRequest;
import org.burgas.identityservice.dto.AuthorityResponse;
import org.burgas.identityservice.handler.IdentityPrincipalHandler;
import org.burgas.identityservice.service.AuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/authorities")
@Tag(
        name = "AuthorityController",
        description = "Контроллер для управления ролями"
)
public class AuthorityController {

    private final AuthorityService authorityService;
    private final IdentityPrincipalHandler identityPrincipalHandler;

    public AuthorityController(
            AuthorityService authorityService,
            IdentityPrincipalHandler identityPrincipalHandler
    ) {
        this.authorityService = authorityService;
        this.identityPrincipalHandler = identityPrincipalHandler;
    }

    @GetMapping
    @Operation(
            summary = "Полный список ролей",
            description = "Получения списка всех доступных ролей"
    )
    public @ResponseBody ResponseEntity<List<AuthorityResponse>> getAllAuthorities(
            @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(
                        identityPrincipalHandler.handleAdminIdentityPrincipal(
                                authorityService.findAll(), authentication
                        )
                );
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Роль по идентификатору",
            description = "Получение роли по идентификатору"
    )
    public @ResponseBody ResponseEntity<AuthorityResponse> getAuthorityById(@RequestParam Long authorityId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(authorityService.findById(authorityId));
    }

    @GetMapping(value = "/by-name")
    @Operation(
            summary = "Роль по наименованию",
            description = "Получение роли по наименованию"
    )
    public @ResponseBody ResponseEntity<AuthorityResponse> getAuthorityByName(@RequestParam String name) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(authorityService.findByName(name));
    }

    @PostMapping(value = "/create")
    @GetMapping(value = "/by-name")
    @Operation(
            summary = "Добавление роли",
            description = "Добавление роли (доступ только для администратора)"
    )
    public @ResponseBody ResponseEntity<Long> createAuthority(
            @RequestBody AuthorityRequest authorityRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        Long authorityId = identityPrincipalHandler.handleAdminIdentityPrincipal(
                authorityService.createOrUpdate(authorityRequest), authentication
        );
        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/authorities/by-id?authorityId=" + authorityId))
                .body(authorityId);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение роли",
            description = "Изменение роли (доступ только для администратора)"
    )
    public @ResponseBody ResponseEntity<Long> updateAuthority(
            @RequestBody AuthorityRequest authorityRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        Long authorityId = identityPrincipalHandler.handleAdminIdentityPrincipal(
                authorityService.createOrUpdate(authorityRequest), authentication
        );
        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/authorities/by-id?authorityId=" + authorityId))
                .body(authorityId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление роли",
            description = "Удаление роли по идентификатору (доступ только для администратора)"
    )
    public @ResponseBody ResponseEntity<String> deleteAuthority(
            @RequestParam Long authorityId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .body(
                        identityPrincipalHandler.handleAdminIdentityPrincipal(
                                authorityService.deleteById(authorityId), authentication
                        )
                );
    }
}

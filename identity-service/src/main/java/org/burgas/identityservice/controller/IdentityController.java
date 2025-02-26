package org.burgas.identityservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.handler.IdentityPrincipalHandler;
import org.burgas.identityservice.service.IdentityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/identities")
@Tag(
        name = "IdentityController",
        description = "Контроллер для управления аккаунтами авторизации и аутентификации"
)
public class IdentityController {

    private final IdentityService identityService;
    private final IdentityPrincipalHandler identityPrincipalHandler;

    public IdentityController(IdentityService identityService, IdentityPrincipalHandler identityPrincipalHandler) {
        this.identityService = identityService;
        this.identityPrincipalHandler = identityPrincipalHandler;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Список пользователей",
            description = "Получение полного списка аккаунтов пользователей"
    )
    public @ResponseBody ResponseEntity<List<IdentityResponse>> getAllIdentities() {
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(identityService.findAll());
    }

    @GetMapping(value = "/by-id", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение пользователя(id)",
            description = "Получение пользователя по идентификатору"
    )
    public @ResponseBody ResponseEntity<IdentityResponse> getIdentityById(@RequestParam Long identityId) {
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(identityService.findById(identityId));
    }

    @GetMapping(value = "/by-email", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение пользователя(email)",
            description = "Получение пользователя по email"
    )
    public @ResponseBody ResponseEntity<IdentityResponse> getIdentityByEmail(@RequestParam String email) {
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(identityService.findByEmail(email));
    }

    @GetMapping(value = "/by-username", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение пользователя(username)",
            description = "Получение пользователя по username"
    )
    public @ResponseBody ResponseEntity<IdentityResponse> getIdentityByUsername(@RequestParam String username) {
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(identityService.findByUsername(username));
    }

    @PostMapping(value = "/create", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Добавление пользователя",
            description = "Добавление аккаунта пользователя"
    )
    public @ResponseBody ResponseEntity<IdentityResponse> createIdentity(
            @RequestBody IdentityRequest identityRequest
    ) {
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(identityService.createOrUpdate(identityRequest, null));
    }

    @PutMapping(value = "/update", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Изменение пользователя",
            description = "Изменение аккаунта пользователя"
    )
    public @ResponseBody ResponseEntity<IdentityResponse> updateIdentity(
            @RequestBody IdentityRequest identityRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(identityService.createOrUpdate(identityRequest, authentication));
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление пользователя",
            description = "Удаление аккаунта пользователя"
    )
    public @ResponseBody ResponseEntity<String> deleteIdentity(
            @RequestParam Long identityId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity.ok(identityService.deleteById(identityId, authentication));
    }

    @PutMapping(value = "/banned")
    @Operation(
            summary = "Забанить пользователя",
            description = "Отключение аккаунта пользователя от функционала"
    )
    public @ResponseBody ResponseEntity<String> banIdentity(
            @RequestParam Long identityId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity.ok(
                identityPrincipalHandler.handleAdminIdentityPrincipal(
                        identityService.addBan(identityId), authentication
                )
        );
    }

    @PutMapping(value = "/unbanned")
    @Operation(
            summary = "Разбанить пользователя",
            description = "Возобновление аккаунта пользователя в правах и предоставление функционала"
    )
    public @ResponseBody ResponseEntity<String> unbanIdentity(
            @RequestParam Long identityId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity.ok(
                identityPrincipalHandler.handleAdminIdentityPrincipal(
                        identityService.unban(identityId), authentication
                )
        );
    }
}

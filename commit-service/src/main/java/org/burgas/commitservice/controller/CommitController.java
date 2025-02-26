package org.burgas.commitservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.burgas.commitservice.dto.CommitResponse;
import org.burgas.commitservice.service.CommitService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/commits")
@Tag(
        name = "CommitController",
        description = "Контроллер для управления сервисом заказов"
)
public class CommitController {

    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @GetMapping(value = "/get-commit")
    @Operation(
            summary = "Получение заказа",
            description = "Получение заказа с помощью сессии и куки клиента"
    )
    public @ResponseBody ResponseEntity<CommitResponse> getCommit(
            HttpServletRequest httpServletRequest,
            @RequestHeader(value = AUTHORIZATION, required = false) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(commitService.findCommit(httpServletRequest, authentication));
    }

    @PostMapping(value = "/close-commit")
    @Operation(
            summary = "Оплата заказа и закрытие счета",
            description = "Оплата и закрытие счета с помощью сессии и идентификатора авторизованного клиента"
    )
    public @ResponseBody ResponseEntity<String> closeCommit(
            HttpServletRequest httpServletRequest,
            @RequestHeader(value = AUTHORIZATION, required = false) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .body(commitService.closeCommit(httpServletRequest, authentication));
    }

    @DeleteMapping("/delete-commit")
    @Operation(
            summary = "Удаление заказа",
            description = "Удаление заказа с помощью сессии и идентификатора авторизованного клиента"
    )
    public @ResponseBody ResponseEntity<String> deleteCommit(
            HttpServletRequest httpServletRequest,
            @RequestHeader(value = AUTHORIZATION, required = false) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .body(commitService.deleteCommit(httpServletRequest, authentication));
    }
}

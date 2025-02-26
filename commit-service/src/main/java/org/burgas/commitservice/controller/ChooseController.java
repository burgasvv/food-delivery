package org.burgas.commitservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.burgas.commitservice.dto.ChooseRequest;
import org.burgas.commitservice.dto.ChooseResponse;
import org.burgas.commitservice.dto.CommitResponse;
import org.burgas.commitservice.mapper.CommitMapper;
import org.burgas.commitservice.repository.CommitRepository;
import org.burgas.commitservice.service.ChooseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/choose")
@Tag(
        name = "ChooseController",
        description = "Контроллер для управления и организации продуктами в заказе в соответствии с сессией запросов"
)
public class ChooseController {

    private final ChooseService chooseService;
    private final CommitRepository commitRepository;
    private final CommitMapper commitMapper;

    public ChooseController(
            ChooseService chooseService,
            CommitRepository commitRepository, CommitMapper commitMapper
    ) {
        this.chooseService = chooseService;
        this.commitRepository = commitRepository;
        this.commitMapper = commitMapper;
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Продукт в заказе по идентификатору",
            description = "Получение продукта в заказе по его идентификатору"
    )
    public @ResponseBody ResponseEntity<ChooseResponse> getChooseById(@RequestParam Long chooseId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(chooseService.findById(chooseId));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление продукта в заказ",
            description = "Добавление продукта в заказ"
    )
    public @ResponseBody ResponseEntity<Long> createChoose(
            @RequestBody ChooseRequest chooseRequest,
            @RequestHeader(value = AUTHORIZATION, required = false) String authentication,
            HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest
    ) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            cookies = new Cookie[0];
        }
        Cookie commitCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("commitCookie"))
                .findFirst()
                .orElse(null);

        if (commitCookie == null) {
            commitCookie = new Cookie("commitCookie", UUID.randomUUID().toString());
            commitCookie.setMaxAge(60 * 60 * 24 * 365 * 10);
            httpServletResponse.addCookie(commitCookie);
        }

        ChooseResponse chooseResponse = chooseService.createOrUpdate(chooseRequest, authentication, commitCookie);
        CommitResponse commitResponse = commitRepository.findById(chooseResponse.getCommitId())
                .map(commitMapper::toCommitResponse)
                .orElseGet(CommitResponse::new);
        httpServletRequest.getSession().setAttribute(commitCookie.getName(), commitResponse);

        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/choose/by-id?chooseId=" + chooseResponse.getId()))
                .body(chooseResponse.getId());
    }

    @PostMapping(value = "/change-choose-amount")
    @Operation(
            summary = "Изменение количества продукта в заказе",
            description = "Изменение количества продукта в заказе (+ 1, -1) продукт"
    )
    public @ResponseBody ResponseEntity<String> changeChooseAmount(
            @RequestParam Long chooseId, @RequestParam Boolean more, HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity
                .status(OK)
                .body(chooseService.changeChooseAmount(chooseId, httpServletRequest, more));
    }
}

package org.burgas.departmentservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.departmentservice.dto.DepartmentRequest;
import org.burgas.departmentservice.dto.DepartmentResponse;
import org.burgas.departmentservice.handler.IdentityPrincipalHandlerDepartmentService;
import org.burgas.departmentservice.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpHeaders.*;

@Controller
@RequestMapping("/departments")
@Tag(
        name = "DepartmentController",
        description = "Контроллер для управления филиалами и отделами"
)
public class DepartmentController {

    private final DepartmentService departmentService;
    private final IdentityPrincipalHandlerDepartmentService identityPrincipalHandlerDepartmentService;

    public DepartmentController(
            DepartmentService departmentService,
            IdentityPrincipalHandlerDepartmentService identityPrincipalHandlerDepartmentService
    ) {
        this.departmentService = departmentService;
        this.identityPrincipalHandlerDepartmentService = identityPrincipalHandlerDepartmentService;
    }

    @GetMapping
    @Operation(
            summary = "Полный список филиалов",
            description = "Получение полного списка филиалов"
    )
    public @ResponseBody ResponseEntity<List<DepartmentResponse>> getDepartmentsAsEvent() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.findAll());
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Филиал по идентификатору",
            description = "Получение филиала по идентификатору"
    )
    public @ResponseBody ResponseEntity<DepartmentResponse> getDepartmentById(@RequestParam Long departmentId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(departmentService.findById(departmentId));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление филиала",
            description = "Добавление данных о филиале и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> createDepartment(
            @RequestBody DepartmentRequest departmentRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        Long departmentId = identityPrincipalHandlerDepartmentService.handleAdminIdentityPrincipal(
                departmentService.createOrUpdate(departmentRequest), authentication
        );
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/departments/by-id?departmentId=" + departmentId))
                .body(departmentId);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение филиала",
            description = "Изменение данных о филиале и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> updateDepartment(
            @RequestBody DepartmentRequest departmentRequest, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        Long departmentId = identityPrincipalHandlerDepartmentService.handleAdminIdentityPrincipal(
                departmentService.createOrUpdate(departmentRequest), authentication
        );
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/departments/by-id?departmentId=" + departmentId))
                .body(departmentId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление филиала",
            description = "Удаление данных о филиале"
    )
    public @ResponseBody ResponseEntity<String> deleteDepartment(
            @RequestParam Long departmentId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        identityPrincipalHandlerDepartmentService.handleAdminIdentityPrincipal(
                                departmentService.deleteById(departmentId), authentication
                        )
                );
    }
}

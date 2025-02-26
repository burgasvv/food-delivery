package org.burgas.employeeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.service.EmployeeService;
import org.burgas.mediaservice.exception.WrongMediatypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.burgas.mediaservice.entity.MediaMessage.WRONG_MEDIATYPE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
@RequestMapping("/employees")
@Tag(
        name = "EmployeeController",
        description = "Контроллер для организации аккаунтов сотрудников"
)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(
            summary = "Список сотрудников компании",
            description = "Получение списка всех сотрудников компании"
    )
    public @ResponseBody ResponseEntity<List<EmployeeResponse>> getAllEmployees(
            @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(employeeService.findAll(authentication));
    }

    @GetMapping("/by-department")
    @Operation(
            summary = "Список сотрудников в филиале",
            description = "Получение списка сотрудников в филиале"
    )
    public @ResponseBody ResponseEntity<List<EmployeeResponse>> getEmployeesByDepartmentId(
            @RequestParam Long departmentId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(employeeService.findByDepartmentId(departmentId, authentication));
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Сотрудник по идентификатору",
            description = "Получение сотрудника по его идентификатору"
    )
    public @ResponseBody ResponseEntity<EmployeeResponse> getEmployeeById(
            @RequestHeader(AUTHORIZATION) String authentication, @RequestParam Long employeeId
    ) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(employeeService.findById(employeeId, authentication));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление данных сотрудника",
            description = "Добавление данных о сотруднике и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> createEmployee(
            @RequestHeader(AUTHORIZATION) String authentication, @RequestBody EmployeeRequest employeeRequest
    ) {
        Long employeeId = employeeService.createOrUpdate(employeeRequest, authentication);
        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/employees/by-id?employeeId=" + employeeId))
                .body(employeeId);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение данных сотрудника",
            description = "Изменение данных о сотруднике и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> updateEmployee(
            @RequestHeader(AUTHORIZATION) String authentication, @RequestBody EmployeeRequest employeeRequest
    ) {
        Long employeeId = employeeService.createOrUpdate(employeeRequest, authentication);
        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/employees/by-id?employeeId=" + employeeId))
                .body(employeeId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление данных сотрудника",
            description = "Удаление данных о сотруднике"
    )
    public @ResponseBody ResponseEntity<String> deleteEmployee(
            @RequestHeader(AUTHORIZATION) String authentication, @RequestParam Long employeeId
    ) {
        return ResponseEntity
                .status(OK)
                .body(employeeService.deleteById(employeeId, authentication));
    }

    @PostMapping(value = "/upload-employee-image", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка изображения для сотрудника",
            description = "Загрузка или обновление изображения для сотрудника"
    )
    public @ResponseBody ResponseEntity<String> uploadEmployeeImage(
            @RequestPart MultipartFile file, @RequestPart String employeeId, @RequestPart(required = false) String previousMediaId,
            @RequestHeader(AUTHORIZATION) String authentication
    ) {
        if (
                Objects.requireNonNull(file.getContentType())
                        .split("/")[0]
                        .equalsIgnoreCase("image")
        ) {
            return ResponseEntity
                    .status(OK)
                    .body(
                            employeeService.uploadEmployeeImage(
                                Long.parseLong(employeeId), previousMediaId == null ? 0L : Long.parseLong(previousMediaId), file, authentication
                            )
                    );
        } else
            throw new WrongMediatypeException(WRONG_MEDIATYPE.getMessage());
    }

    @DeleteMapping(value = "/delete-employee-image")
    @Operation(summary = "Удаление изображения сотрудника")
    public @ResponseBody ResponseEntity<String> deleteEmployeeImage(
            @RequestParam Long employeeId, @RequestHeader(AUTHORIZATION) String authentication
    ) {
        return ResponseEntity
                .status(OK)
                .body(employeeService.deleteEmployeeImage(employeeId, authentication));
    }
}

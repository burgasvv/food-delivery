package org.burgas.foodservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.foodservice.entity.Capacity;
import org.burgas.foodservice.service.CapacityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/capacities")
@Tag(
        name = "CapacityController",
        description = "Контроллер для управления емкостями продуктов"
)
public class CapacityController {

    private final CapacityService capacityService;

    public CapacityController(CapacityService capacityService) {
        this.capacityService = capacityService;
    }

    @GetMapping
    @Operation(
            summary = "Список емкостей",
            description = "Получение списка доступных емкостей"
    )
    public @ResponseBody ResponseEntity<List<Capacity>> getCapacities() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(capacityService.findAll());
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Емкость по идентификатору",
            description = "Получение емкости по идентификатору"
    )
    public @ResponseBody ResponseEntity<Capacity> getCapacityById(@RequestParam Long capacityId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(capacityService.findById(capacityId));
    }

    @GetMapping(value = "/by-liters")
    @Operation(
            summary = "Емкость и вместимость",
            description = "Получение емкости по заданной вместимости"
    )
    public @ResponseBody ResponseEntity<Capacity> getCapacityByLiters(@RequestParam Float liters) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(capacityService.findByLiters(liters));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление емкости",
            description = "Добавить данные о емкости и получить(переадресация) ее"
    )
    public @ResponseBody ResponseEntity<Long> createCapacity(@RequestBody Capacity capacity) {
        Long capacityId = capacityService.createOrUpdate(capacity);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/capacities/by-id?capacityId=" + capacityId))
                .body(capacityId);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение емкости",
            description = "Изменить данные о емкости и получить(переадресация) ее"
    )
    public @ResponseBody ResponseEntity<Long> updateCapacity(@RequestBody Capacity capacity) {
        Long capacityId = capacityService.createOrUpdate(capacity);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/capacities/by-id?capacityId=" + capacityId))
                .body(capacityId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление емкости",
            description = "Удалить данные о емкости"
    )
    public @ResponseBody ResponseEntity<String> deleteCapacity(@RequestParam Long capacityId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(capacityService.deleteById(capacityId));
    }
}

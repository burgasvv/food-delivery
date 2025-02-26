package org.burgas.foodservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.foodservice.entity.Size;
import org.burgas.foodservice.service.SizeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/sizes")
@Tag(
        name = "SizeController",
        description = "Контроллер для управление доступными размерами еды"
)
public class SizeController {

    private final SizeService sizeService;

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping
    @Operation(
            summary = "Получение размеров",
            description = "Получение всех доступных размеров еды"
    )
    public @ResponseBody ResponseEntity<List<Size>> getAllSizes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sizeService.findAll());
    }

    @GetMapping("/by-foodId")
    @Operation(
            summary = "Размеры по идентификатору еды",
            description = "Получение размеров доступных для того или иного блюда"
    )
    public @ResponseBody ResponseEntity<List<Size>> getSizesByFoodId(@RequestParam Long foodId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sizeService.findSizesByFoodId(foodId));
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Размер по идентификатору",
            description = "Получение размера по его идентификатору"
    )
    public @ResponseBody ResponseEntity<Size> getSizeById(@RequestParam Long sizeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sizeService.findById(sizeId));
    }

    @GetMapping(value = "/by-size")
    @Operation(
            summary = "Получение размера"
    )
    public @ResponseBody ResponseEntity<Size> getSizeBySize(@RequestParam Long size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sizeService.findBySize(size));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление размера",
            description = "Добавление данных о размере и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> createSize(@RequestBody Size size) {
        Long sizeId = sizeService.createOrUpdate(size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .location(URI.create("/sizes/by-id?sizeId=" + sizeId))
                .body(sizeId);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение размера",
            description = "Изменение данных о размере и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> updateSize(@RequestBody Size size) {
        Long sizeId = sizeService.createOrUpdate(size);
        return ResponseEntity
                .status(HttpStatus.OK)
                .location(URI.create("/sizes/by-id?sizeId=" + sizeId))
                .body(sizeId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление размера",
            description = "Удаление данных о размере"
    )
    public @ResponseBody ResponseEntity<String> deleteSizeById(@RequestParam Long sizeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sizeService.deleteById(sizeId));
    }
}

package org.burgas.foodservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.foodservice.dto.ComboRequest;
import org.burgas.foodservice.dto.ComboResponse;
import org.burgas.foodservice.service.ComboService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.burgas.foodservice.dto.MediaMessage.WRONG_MEDIATYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping("/combos")
@Tag(
        name = "ComboController",
        description = "Контроллер для управления наборами из блюд"
)
public class ComboController {

    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @GetMapping
    @Operation(
            summary = "Все наборы",
            description = "Получение всех доступных наборов"
    )
    public @ResponseBody ResponseEntity<List<ComboResponse>> getAllCombos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(APPLICATION_JSON)
                .body(comboService.findAll());
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Набор по идентификатору",
            description = "Получение набора по идентификатору"
    )
    public @ResponseBody ResponseEntity<ComboResponse> getComboById(@RequestParam Long comboId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(APPLICATION_JSON)
                .body(comboService.findById(comboId));
    }

    @GetMapping(value = "/by-name")
    @Operation(
            summary = "Набор по наименованию",
            description = "Получение набора по наименованию"
    )
    public @ResponseBody ResponseEntity<ComboResponse> getComboByName(@RequestParam String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(APPLICATION_JSON)
                .body(comboService.findByName(name));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление набора",
            description = "Добавление данных о наборе с едой и переадресация на ее страницу"
    )
    public @ResponseBody ResponseEntity<Long> createCombo(@RequestBody ComboRequest comboRequest) {
        Long comboId = comboService.createOrUpdate(comboRequest);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/combos/by-id?comboId=" + comboId))
                .body(comboId);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Добавление набора",
            description = "Добавление данных о наборе с едой и переадресация на ее страницу"
    )
    public @ResponseBody ResponseEntity<Long> updateCombo(@RequestBody ComboRequest comboRequest) {
        Long comboId = comboService.createOrUpdate(comboRequest);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/combos/by-id?comboId=" + comboId))
                .body(comboId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление набора",
            description = "Удаление данных о наборе с едой"
    )
    public @ResponseBody ResponseEntity<String> deleteCombo(@RequestParam Long comboId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(TEXT_PLAIN)
                .body(comboService.deleteById(comboId));
    }

    @PostMapping(value = "/upload-combo-image", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка изображения для комбо",
            description = "Добавление или смена изображения для комбо"
    )
    public @ResponseBody ResponseEntity<String> uploadImage(
            @RequestPart String comboId, @RequestPart(required = false) String previousMediaId, @RequestPart MultipartFile file
    ) {
        if (
                Objects.requireNonNull(file.getContentType())
                        .split("/")[0]
                        .equals("image")
        ) {
            return ResponseEntity
                    .status(OK)
                    .contentType(TEXT_PLAIN)
                    .body(
                            comboService.uploadComboImage(
                                    Long.valueOf(comboId),
                                    previousMediaId == null ? 0L : Long.parseLong(previousMediaId),
                                    file
                            )
                    );
        } else
            throw new RuntimeException(WRONG_MEDIATYPE.getMessage());
    }

    @DeleteMapping("/delete-combo-image")
    @Operation(summary = "Удаление изображения для комбо")
    public @ResponseBody ResponseEntity<String> deleteImage(@RequestParam Long comboId) {
        return ResponseEntity
                .status(OK)
                .contentType(TEXT_PLAIN)
                .body(comboService.deleteComboImage(comboId));
    }
}

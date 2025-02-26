package org.burgas.foodservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.foodservice.dto.FoodRequest;
import org.burgas.foodservice.dto.FoodResponse;
import org.burgas.foodservice.service.FoodService;
import org.burgas.mediaservice.exception.WrongMediatypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.burgas.mediaservice.entity.MediaMessage.WRONG_MEDIATYPE;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping("/food")
@Tag(
        name = "FoodController",
        description = "Контроллер для управления сервисом с едой"
)
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping
    @Operation(
            summary = "Все блюда",
            description = "Получение всех доступных блюд"
    )
    public @ResponseBody ResponseEntity<List<FoodResponse>> getAllFood() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(foodService.findAll());
    }

    @GetMapping(value = "/server-events")
    @Operation(
            summary = "Все блюда, событие от сервера",
            description = "Получение всех доступных блюд в виде события от сервера"
    )
    public ResponseEntity<SseEmitter> getAllFoodSse() {
        SseEmitter sseEmitter = new SseEmitter();
        CompletableFuture
                .runAsync(
                        () ->
                            foodService.findAll().forEach(
                                    foodResponse -> {
                                        try {
                                            Thread.sleep(300);

                                            Set<ResponseBodyEmitter.DataWithMediaType> data = SseEmitter.event()
                                                    .id(String.valueOf(foodResponse.getId()))
                                                    .name("Food: " + foodResponse.getName())
                                                    .comment(foodResponse.getDescription())
                                                    .data(foodResponse)
                                                    .build();

                                            sseEmitter.send(data);

                                        } catch (IOException | InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                            )
                );
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_EVENT_STREAM, UTF_8))
                .body(sseEmitter);
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Блюдо по идентификатору",
            description = "Получение блюда по идентификатору"
    )
    public @ResponseBody ResponseEntity<FoodResponse> getFoodById(@RequestParam Long foodId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(foodService.findById(foodId));
    }

    @GetMapping(value = "/by-name")
    @Operation(
            summary = "Блюдо по наименованию",
            description = "Получение блюда по наименованию"
    )
    public @ResponseBody ResponseEntity<FoodResponse> getFoodByName(@RequestParam String name) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(foodService.findByName(name));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление блюда",
            description = "Добавление данных о еде и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> createFood(@RequestBody FoodRequest foodRequest) {
        Long foodId = foodService.createOrUpdate(foodRequest);
        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/food/by-id?foodId=" + foodId))
                .body(foodId);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение блюда",
            description = "Изменение данных о еде и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> updateFood(@RequestBody FoodRequest foodRequest) {
        Long foodId = foodService.createOrUpdate(foodRequest);
        return ResponseEntity
                .status(FOUND)
                .location(URI.create("/food/by-id?foodId=" + foodId))
                .body(foodId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление блюда",
            description = "Удаление данных о еде"
    )
    public @ResponseBody ResponseEntity<String> deleteFood(@RequestParam Long foodId) {
        return ResponseEntity
                .status(OK)
                .body(foodService.deleteById(foodId));
    }

    @PostMapping(value = "/upload-food-image", consumes = MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка изображения для блюда",
            description = "Добавление или смена изображения для блюда"
    )
    public @ResponseBody ResponseEntity<String> uploadImage(
            @RequestPart String foodId, @RequestPart(required = false) String previousMediaId, @RequestPart MultipartFile file
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
                            foodService.uploadFoodImage(
                                    Long.valueOf(foodId),
                                    previousMediaId == null ? 0L : Long.parseLong(previousMediaId),
                                    file
                            )
                    );
        } else
            throw new WrongMediatypeException(WRONG_MEDIATYPE.getMessage());
    }

    @DeleteMapping("/delete-food-image")
    @Operation(summary = "Удаление изображения для блюда")
    public @ResponseBody ResponseEntity<String> deleteImage(@RequestParam Long foodId) {
        return ResponseEntity
                .status(OK)
                .contentType(TEXT_PLAIN)
                .body(foodService.deleteFoodImage(foodId));
    }
}

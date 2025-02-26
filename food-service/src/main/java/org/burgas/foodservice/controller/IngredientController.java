package org.burgas.foodservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.foodservice.entity.Ingredient;
import org.burgas.foodservice.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/ingredients")
@Tag(
        name = "IngredientController",
        description = "Контроллер для управления ингредиентами к пище"
)
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @Operation(
            summary = "Все доступные ингредиенты",
            description = "Получение всех доступных ингредиентов"
    )
    public @ResponseBody ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ingredientService.findAll());
    }

    @GetMapping(value = "/by-foodId")
    @Operation(
            summary = "Список ингредиентов к конкретному блюду",
            description = "Получение списка ингредиентов специально подобранных к блюду"
    )
    public @ResponseBody ResponseEntity<List<Ingredient>> getIngredientsByFoodId(@RequestParam Long foodId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ingredientService.findByFoodId(foodId));
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Ингредиент по идентификатору",
            description = "Получение ингредиента по его идентификатору"
    )
    public @ResponseBody ResponseEntity<Ingredient> getIngredientById(@RequestParam Long ingredientId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ingredientService.findById(ingredientId));
    }

    @GetMapping(value = "/by-name")
    @Operation(
            summary = "Ингредиент по наименованию",
            description = "Получение ингредиента по его наименованию"
    )
    public @ResponseBody ResponseEntity<Ingredient> getIngredientByName(@RequestParam String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ingredientService.findByName(name));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление ингредиента",
            description = "Добавление данных об ингредиенте и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> createIngredient(@RequestBody Ingredient ingredient) {
        Long ingredientId = ingredientService.createOrUpdate(ingredient);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/ingredients/by-id?ingredientId=" + ingredientId))
                .body(ingredientId);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение ингредиента",
            description = "Изменение данных об ингредиенте и переадресация на страницу"
    )
    public @ResponseBody ResponseEntity<Long> updateIngredient(@RequestBody Ingredient ingredient) {
        Long ingredientId = ingredientService.createOrUpdate(ingredient);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/ingredients/by-id?ingredientId=" + ingredientId))
                .body(ingredientId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление ингредиента",
            description = "Удаление данных об ингредиенте"
    )
    public @ResponseBody ResponseEntity<String> deleteIngredient(@RequestParam Long ingredientId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ingredientService.deleteById(ingredientId));
    }
}

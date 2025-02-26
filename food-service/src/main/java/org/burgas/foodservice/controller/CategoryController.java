package org.burgas.foodservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.foodservice.entity.Category;
import org.burgas.foodservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/categories")
@Tag(
        name = "CategoryController",
        description = "Контроллер для управления категориями еды"
)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(
            summary = "Категории еды",
            description = "Получение всех категорий еды"
    )
    public @ResponseBody ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.findAll());
    }

    @GetMapping(value = "/by-id")
    @Operation(
            summary = "Категория по идентификатору",
            description = "Получение категории по ее идентификатору"
    )
    public @ResponseBody ResponseEntity<Category> getCategoryById(@RequestParam Long categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.findById(categoryId));
    }

    @GetMapping(value = "/by-name")
    @Operation(
            summary = "Категория по наименованию",
            description = "Получение категории по ее наименованию"
    )
    public @ResponseBody ResponseEntity<Category> getCategoryByName(@RequestParam String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.findByName(name));
    }

    @PostMapping(value = "/create")
    @Operation(
            summary = "Добавление категории",
            description = "Добавление данных о категории и переадресация на страницу категории"
    )
    public @ResponseBody ResponseEntity<Long> createCategory(@RequestBody Category category) {
        Long categoryId = categoryService.createOrUpdate(category);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/categories/by-id?categoryId=" + categoryId))
                .body(categoryId);
    }

    @PostMapping(value = "/update")
    @Operation(
            summary = "Изменение категории",
            description = "Изменение данных о категории и переадресация на страницу категории"
    )
    public @ResponseBody ResponseEntity<Long> updateCategory(@RequestBody Category category) {
        Long categoryId = categoryService.createOrUpdate(category);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/categories/by-id?categoryId=" + categoryId))
                .body(categoryId);
    }

    @DeleteMapping(value = "/delete")
    @Operation(
            summary = "Удаление категории",
            description = "Удаление данных о категории"
    )
    public @ResponseBody ResponseEntity<String> deleteCategory(@RequestParam Long categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.deleteById(categoryId));

    }
}

package org.burgas.foodservice.handler;

import io.swagger.v3.oas.annotations.Hidden;
import org.burgas.foodservice.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CapacityNotFoundException.class)
    public ResponseEntity<String> handleCapacityNotFoundException(CapacityNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_PLAIN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_PLAIN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ComboNotFoundException.class)
    public ResponseEntity<String> handleComboNotFoundException(ComboNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_PLAIN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseEntity<String> handleFoodNotFoundException(FoodNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_PLAIN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity<String> handleIngredientNotFoundException(IngredientNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_PLAIN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(SizeNotFoundException.class)
    public ResponseEntity<String> handleSizeNotFoundException(SizeNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_PLAIN)
                .body(exception.getMessage());
    }
}

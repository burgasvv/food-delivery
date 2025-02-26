package org.burgas.mediaservice.handler;

import io.swagger.v3.oas.annotations.Hidden;
import org.burgas.mediaservice.exception.MediaNotFoundException;
import org.burgas.mediaservice.exception.MultipartEmptyException;
import org.burgas.mediaservice.exception.WrongMediatypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartEmptyException.class)
    public ResponseEntity<String> handleMultipartEmptyException(MultipartEmptyException exception) {
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .contentType(TEXT_PLAIN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(WrongMediatypeException.class)
    public ResponseEntity<String> handleWrongMediatypeException(WrongMediatypeException exception) {
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .contentType(TEXT_PLAIN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MediaNotFoundException.class)
    public ResponseEntity<String> handleMediaNotFoundException(MediaNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_PLAIN)
                .body(exception.getMessage());
    }
}

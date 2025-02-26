package org.burgas.commitservice.handler;

import io.swagger.v3.oas.annotations.Hidden;
import org.burgas.commitservice.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommitAlreadyClosedException.class)
    public ResponseEntity<String> handleCommitAlreadyClosedException(CommitAlreadyClosedException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }

    @ExceptionHandler(CommitIsNotClosedException.class)
    public ResponseEntity<String> handleCommitIsNotClosedException(CommitIsNotClosedException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<String> handleNotAuthorizedException(NotAuthorizedException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }

    @ExceptionHandler(NoUserCommitsException.class)
    public ResponseEntity<String> handleNoUserCommitsException(NoUserCommitsException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<String> handleTokenNotFoundException(TokenNotFoundException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }

    @ExceptionHandler(ChooseNotFoundException.class)
    public ResponseEntity<String> handleChooseNotFoundException(ChooseNotFoundException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }

    @ExceptionHandler(CommitNotFoundException.class)
    public ResponseEntity<String> handleCommitNotFoundException(CommitNotFoundException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }
}

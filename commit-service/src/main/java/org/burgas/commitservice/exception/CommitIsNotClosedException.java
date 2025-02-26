package org.burgas.commitservice.exception;

public class CommitIsNotClosedException extends RuntimeException {

    public CommitIsNotClosedException(String message) {
        super(message);
    }
}

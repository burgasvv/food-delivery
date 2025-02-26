package org.burgas.commitservice.exception;

public class CommitAlreadyClosedException extends RuntimeException {

    public CommitAlreadyClosedException(String message) {
        super(message);
    }
}

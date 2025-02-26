package org.burgas.commitservice.exception;

public class NoUserCommitsException extends RuntimeException {

    public NoUserCommitsException(String message) {
        super(message);
    }
}

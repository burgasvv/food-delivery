package org.burgas.identityservice.exception;

public class NotAuthorizedException extends RuntimeException {

    private final String message;

    public NotAuthorizedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

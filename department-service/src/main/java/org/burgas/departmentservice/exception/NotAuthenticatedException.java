package org.burgas.departmentservice.exception;

public class NotAuthenticatedException extends RuntimeException {

    private final String message;

    public NotAuthenticatedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

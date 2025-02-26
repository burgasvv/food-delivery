package org.burgas.identityservice.exception;

public class IdentityNotFoundException extends RuntimeException {

    private final String message;

    public IdentityNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

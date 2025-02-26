package org.burgas.identityservice.exception;

public class AuthorityNotFoundException extends RuntimeException {

    private final String message;

    public AuthorityNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

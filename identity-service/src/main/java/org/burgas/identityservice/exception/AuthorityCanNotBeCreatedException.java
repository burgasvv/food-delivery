package org.burgas.identityservice.exception;

public class AuthorityCanNotBeCreatedException extends RuntimeException {

    private final String message;

    public AuthorityCanNotBeCreatedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

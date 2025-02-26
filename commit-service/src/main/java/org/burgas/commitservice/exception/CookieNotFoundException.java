package org.burgas.commitservice.exception;

public class CookieNotFoundException extends RuntimeException {

    public CookieNotFoundException(String message) {
        super(message);
    }
}

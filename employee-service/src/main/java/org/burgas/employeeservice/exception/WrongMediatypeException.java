package org.burgas.employeeservice.exception;

public class WrongMediatypeException extends RuntimeException {

    public WrongMediatypeException(String message) {
        super(message);
    }
}

package org.burgas.foodservice.exception;

public class CapacityNotFoundException extends RuntimeException {

    public CapacityNotFoundException(String message) {
        super(message);
    }
}

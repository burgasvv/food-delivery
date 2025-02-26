package org.burgas.departmentservice.exception;

public class AddressNotFoundException extends RuntimeException {

    private final String message;

    public AddressNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

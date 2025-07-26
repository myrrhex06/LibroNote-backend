package com.libronote.common.exception;

public class AlreadyRegistrationException extends RuntimeException {

    public AlreadyRegistrationException() {
        super();
    }

    public AlreadyRegistrationException(String message) {
        super(message);
    }

    public AlreadyRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}

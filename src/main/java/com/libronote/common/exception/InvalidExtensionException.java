package com.libronote.common.exception;

public class InvalidExtensionException extends RuntimeException {

    public InvalidExtensionException() {
        super();
    }

    public InvalidExtensionException(String message) {
        super(message);
    }

    public InvalidExtensionException(String message, Throwable cause) {
        super(message, cause);
    }
}

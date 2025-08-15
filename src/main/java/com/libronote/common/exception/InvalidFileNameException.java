package com.libronote.common.exception;

public class InvalidFileNameException extends RuntimeException {

    public InvalidFileNameException() {
        super();
    }

    public InvalidFileNameException(String message) {
        super(message);
    }

    public InvalidFileNameException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.libronote.common.exception;

public class InvalidDirectoryPathException extends RuntimeException {

    public InvalidDirectoryPathException() {
    }

    public InvalidDirectoryPathException(String message) {
        super(message);
    }

    public InvalidDirectoryPathException(String message, Throwable cause) {
        super(message, cause);
    }
}

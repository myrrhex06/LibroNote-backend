package com.libronote.common.exception;

public class FileShowException extends RuntimeException {

    public FileShowException() {
    }

    public FileShowException(String message) {
        super(message);
    }

    public FileShowException(String message, Throwable cause) {
        super(message, cause);
    }
}

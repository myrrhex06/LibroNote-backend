package com.libronote.common.exception;

public class FileUpdateFailException extends RuntimeException {

    public FileUpdateFailException() {
    }

    public FileUpdateFailException(String message) {
        super(message);
    }

    public FileUpdateFailException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.libronote.common.exception;

public class FileDeleteFailException extends RuntimeException {

    public FileDeleteFailException() {
    }

    public FileDeleteFailException(String message) {
        super(message);
    }

    public FileDeleteFailException(String message, Throwable cause) {
        super(message, cause);
    }
}

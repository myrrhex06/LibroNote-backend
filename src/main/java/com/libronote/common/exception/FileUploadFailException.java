package com.libronote.common.exception;

public class FileUploadFailException extends RuntimeException {

    public FileUploadFailException() {
        super();
    }

    public FileUploadFailException(String message) {
        super(message);
    }

    public FileUploadFailException(String message, Throwable cause) {
        super(message, cause);
    }
}

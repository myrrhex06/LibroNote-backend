package com.libronote.common.exception;

public class OtherUserUploadFileException extends RuntimeException {

    public OtherUserUploadFileException() {
    }

    public OtherUserUploadFileException(String message) {
        super(message);
    }

    public OtherUserUploadFileException(String message, Throwable cause) {
        super(message, cause);
    }
}

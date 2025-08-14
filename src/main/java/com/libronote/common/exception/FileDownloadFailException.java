package com.libronote.common.exception;

public class FileDownloadFailException extends RuntimeException {

    public FileDownloadFailException() {
    }

    public FileDownloadFailException(String message) {
        super(message);
    }

    public FileDownloadFailException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.libronote.common.exception;

public class RefreshTokenUpdateFailException extends RuntimeException {
    public RefreshTokenUpdateFailException() {
        super();
    }

    public RefreshTokenUpdateFailException(String message) {
        super(message);
    }

    public RefreshTokenUpdateFailException(String message, Throwable cause) {
        super(message, cause);
    }
}

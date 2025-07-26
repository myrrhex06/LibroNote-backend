package com.libronote.common.exception;

public class RefreshTokenInsertFailException extends RuntimeException {

    public RefreshTokenInsertFailException() {
        super();
    }

    public RefreshTokenInsertFailException(String message) {
        super(message);
    }

    public RefreshTokenInsertFailException(String message, Throwable cause) {
        super(message, cause);
    }
}

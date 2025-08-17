package com.libronote.common.exception;

public class PasswordNotMatchesException extends RuntimeException {

    public PasswordNotMatchesException() {
        super();
    }

    public PasswordNotMatchesException(String message) {
        super(message);
    }

    public PasswordNotMatchesException(String message, Throwable cause) {
        super(message, cause);
    }
}

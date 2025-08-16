package com.libronote.common.exception;

public class OtherUserHandleException extends RuntimeException {

    public OtherUserHandleException() {
    }

    public OtherUserHandleException(String message) {
        super(message);
    }

    public OtherUserHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}

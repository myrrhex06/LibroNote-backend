package com.libronote.common.exception;

public class OtherUserBookHandleException extends RuntimeException {

    public OtherUserBookHandleException() {
    }

    public OtherUserBookHandleException(String message) {
        super(message);
    }

    public OtherUserBookHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}

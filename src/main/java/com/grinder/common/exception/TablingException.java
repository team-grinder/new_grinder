package com.grinder.common.exception;

public class TablingException extends RuntimeException{

    public TablingException(String message) {
        super(message);
    }

    public TablingException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.grinder.domain.cart.exception;

public class UnorderedCartAlreadyExistsException extends RuntimeException {
    public UnorderedCartAlreadyExistsException(String message) {
        super(message);
    }
    public UnorderedCartAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.grinder.common.exception.handler;

import com.grinder.domain.cart.exception.UnorderedCartAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handlePasswordValidationException(IllegalArgumentException e) {

        log.error("IllegalArgumentException: {}", e.getMessage());
        return "/main/";
    }

    @ExceptionHandler(UnorderedCartAlreadyExistsException.class)
    public String handleUnorderedCartAlreadyExistsException(UnorderedCartAlreadyExistsException e) {

        log.error("UnorderedCartAlreadyExistsException: {}", e.getMessage());
        return "/main/";
    }
}

package com.grinder.common.security.common.handler;

import com.grinder.common.exception.PasswordValidationException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.common.model.ErrorResult;
import com.grinder.common.model.FailureResult;
import com.grinder.domain.cart.exception.UnorderedCartAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(PasswordValidationException.class)
    public ResponseEntity<FailureResult<ErrorResult>> handlePasswordValidationException(PasswordValidationException e) {
        FailureResult<ErrorResult> failureResult = FailureResult.from(e.getAuthResultEnum());
        return ResponseEntity.status(Integer.parseInt(e.getAuthResultEnum().getStatus())).body(failureResult);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FailureResult<ErrorResult>> handleBindException(BindException e) {
        FieldError fieldError = e.getFieldError();
        AuthResultEnum authResultEnum;

        if (fieldError != null) {
            switch (fieldError.getField()) {
                case "email":
                    authResultEnum = AuthResultEnum.INVALID_EMAIL;
                    break;
                case "nickname":
                    authResultEnum = AuthResultEnum.INVALID_NICKNAME;
                    break;
                default:
                    authResultEnum = AuthResultEnum.INVALID_PARAMETER;
            }
        } else {
            authResultEnum = AuthResultEnum.INVALID_PARAMETER;
        }

        FailureResult<ErrorResult> failureResult = FailureResult.from(authResultEnum);
        return ResponseEntity.status(HttpStatus.valueOf(Integer.parseInt(authResultEnum.getStatus()))).body(failureResult);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FailureResult<ErrorResult>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return handleBindException(new BindException(e.getBindingResult()));
    }
    @ExceptionHandler(UnorderedCartAlreadyExistsException.class)
    public String handleUnorderedCartAlreadyExistsException(UnorderedCartAlreadyExistsException e) {

        log.error("UnorderedCartAlreadyExistsException: {}", e.getMessage());
        return "/main/";
    }
}

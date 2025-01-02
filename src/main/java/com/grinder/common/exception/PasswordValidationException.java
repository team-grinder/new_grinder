package com.grinder.common.exception;

import com.grinder.common.model.AuthResultEnum;

public class PasswordValidationException extends RuntimeException {
    private AuthResultEnum authResultEnum;
    public PasswordValidationException(AuthResultEnum authResultEnum) {
        super(authResultEnum.getMessage());
        this.authResultEnum = authResultEnum;
    }

    public PasswordValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthResultEnum getAuthResultEnum() {
        return this.authResultEnum;
    }


}

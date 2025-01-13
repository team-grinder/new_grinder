package com.grinder.common.exception;

import com.grinder.common.model.AuthResultEnum;

public class MemberException extends RuntimeException {
    private AuthResultEnum authResultEnum;
    public MemberException(AuthResultEnum authResultEnum) {
        super(authResultEnum.getMessage());
        this.authResultEnum = authResultEnum;
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthResultEnum getAuthResultEnum() {
        return this.authResultEnum;
    }
}

package com.grinder.common.utils;

import com.grinder.common.exception.PasswordValidationException;

import com.grinder.common.model.AuthResultEnum;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";
    private static final Pattern PATTERN = Pattern.compile(PASSWORD_PATTERN);

    public static void validatePassword(String password, String confirmPassword) {
        if (!PATTERN.matcher(password).matches()) {
            throw new PasswordValidationException(AuthResultEnum.INVALID_PASSWORD);
        }
        if (!password.equals(confirmPassword)) {
            throw new PasswordValidationException(AuthResultEnum.PASSWORD_MISMATCH);
        }
    }
}
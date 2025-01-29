package com.grinder.common.utils;

import com.grinder.common.security.AuthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticateUtils {
    public static String getAuthEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static Long getAuthId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // 인증되지 않은 상태거나 authentication이 없다면 null 혹은 예외처리
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof AuthenticatedUser)) {
            // 예상과 다른 타입인 경우 처리
            return null;
        }

        return ((AuthenticatedUser) principal).getId();
    }
}

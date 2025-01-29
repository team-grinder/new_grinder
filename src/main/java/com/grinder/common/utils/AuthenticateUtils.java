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
        AuthenticatedUser principal = (AuthenticatedUser) authentication.getPrincipal();
        return principal.getId();
    }
}

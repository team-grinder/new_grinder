package com.grinder.common.security.common.filter;

import com.grinder.common.security.common.model.MemberType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final MemberType memberType; // 예: "COMMON", "ADMIN"

    public CustomAuthenticationToken(Object principal, Object credentials, MemberType memberType) {
        super(principal, credentials);
        this.memberType = memberType;
    }

    public MemberType getLoginType() {
        return memberType;
    }
}
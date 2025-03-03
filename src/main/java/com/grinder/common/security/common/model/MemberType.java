package com.grinder.common.security.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {
    ADMIN("ADMIN"),
    COMMON("COMMON");

    private final String value;
}

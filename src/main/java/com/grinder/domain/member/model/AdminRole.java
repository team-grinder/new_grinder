package com.grinder.domain.member.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminRole {
    OWNER("소유자"),
    MANAGER("매니저"),
    STAFF("직원");

    private final String description;
}

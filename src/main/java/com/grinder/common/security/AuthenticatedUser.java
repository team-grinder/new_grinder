package com.grinder.common.security;


import com.grinder.domain.member.model.TierType;

public interface AuthenticatedUser {
    Long getId();
    String getNickname();
    String getImageUrl();

    TierType getTier();
}

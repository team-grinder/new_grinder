package com.grinder.common.security;


public interface AuthenticatedUser {
    Long getId();
    String getNickname();
    String getImageUrl();
}

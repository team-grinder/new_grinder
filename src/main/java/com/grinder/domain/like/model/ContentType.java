package com.grinder.domain.like.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    MEMBER("회원"),
    FEED("피드"),
    CAFE_LOGO("카페 로고"),
    CAFE_IMAGE("카페 이미지"),
    MENU("메뉴"),
    COMMENT("댓글");

    private final String value;
}
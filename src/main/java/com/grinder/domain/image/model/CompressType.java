package com.grinder.domain.image.model;

import com.grinder.domain.like.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum CompressType {
    ORIGINAL(1, ""),
    HIGH(0.9, "_high"),
    LARGE(0.8, "_large"),
    MEDIUM(0.7, "_medium"),
    SMALL(0.6, "_small"),
    LOW(0.5, "_low");

    private final double scale;
    private final String suffix;

    public static List<CompressType> of(ContentType contentType) {
        switch (contentType) {
            case MEMBER:
                return List.of(MEDIUM, SMALL, LOW);
            case CAFE_IMAGE:
            case FEED:
                return List.of(LARGE, MEDIUM, SMALL);
            case CAFE_LOGO:
            case MENU:
                return List.of(MEDIUM, SMALL);
            default:
                throw new IllegalArgumentException("알 수 없는 ContentType 입니다.");
        }
    }
}

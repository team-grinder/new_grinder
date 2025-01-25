package com.grinder.domain.image.model;

import com.grinder.domain.like.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum CompressType {
    HIGH("1920", "_high"),
    LARGE("1024", "_large"),
    MEDIUM("512", "_medium"),
    SMALL("256", "_small"),
    LOW("128", "_low");

    private final String value;
    private final String suffix;

    public static List<CompressType> of(ContentType contentType) {
        switch (contentType) {
            case MEMBER:
                return List.of(MEDIUM, SMALL, LOW);
            case FEED:
                return List.of(LARGE, MEDIUM, SMALL);
            case CAFE:
            case MENU:
                return List.of(MEDIUM, SMALL);
            default:
                throw new IllegalArgumentException("알 수 없는 ContentType 입니다.");
        }
    }
}

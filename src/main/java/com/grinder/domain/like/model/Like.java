package com.grinder.domain.like.model;

import com.grinder.domain.like.entity.LikeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Like {
    private Long memberId;
    private Long contentId;
    private ContentType contentType;

    public static Like of(Long memberId, Long contentId, ContentType contentType) {
        return new Like(memberId, contentId, contentType);
    }

    public LikeEntity toEntity() {
        return new LikeEntity(null, memberId, contentId, contentType);
    }
}

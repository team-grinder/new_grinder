package com.grinder.domain.comment.model;

import com.grinder.domain.comment.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCommentRequest {
    private Long feedId;
    private String content;
    private Long parentCommentId;

    public CommentEntity toEntity(Long memberId) {
        return new CommentEntity(feedId, memberId, content, parentCommentId);
    }
}

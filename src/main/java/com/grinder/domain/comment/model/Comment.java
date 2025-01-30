package com.grinder.domain.comment.model;

import com.grinder.common.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long feedId;
    private Long memberId;
    private String nickname;
    private String content;
    private Long parentCommentId;
    private String createdAt;
    private boolean isMine;

    public Comment(Long id, Long feedId, Long memberId, String nickname, String content, Long parentCommentId, LocalDateTime createdAt, boolean isMine) {
        this.id = id;
        this.feedId = feedId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.createdAt = DateUtils.parseYYYYMMDD(createdAt);
        this.isMine = isMine;
    }
}

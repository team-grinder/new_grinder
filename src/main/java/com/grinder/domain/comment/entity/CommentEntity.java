package com.grinder.domain.comment.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.common.utils.DateUtils;
import com.grinder.domain.comment.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long feedId;

    @JoinColumn(nullable = false)
    private Long memberId;

    @Column(nullable = false, length = 200)
    private String content;

    @Column(nullable = false)
    private Boolean isVisible;

    private Long parentCommentId;

    @PrePersist
    public void prePersist() {
        isVisible = isVisible == null || isVisible;
    }

    public CommentEntity(Long feedId, Long memberId, String content, Long parentCommentId) {
        this.feedId = feedId;
        this.memberId = memberId;
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    public Comment toComment() {
        return Comment.builder()
                .id(id)
                .feedId(feedId)
                .memberId(memberId)
                .content(content)
                .parentCommentId(parentCommentId)
                .createdAt(DateUtils.parseYYYYMMDD(getCreateDate()))
                .build();
    }
}
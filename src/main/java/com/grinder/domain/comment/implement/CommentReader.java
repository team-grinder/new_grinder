package com.grinder.domain.comment.implement;

import com.grinder.common.model.Slices;
import com.grinder.domain.comment.entity.CommentEntity;
import com.grinder.domain.comment.model.Comment;
import com.grinder.domain.comment.model.CreateCommentRequest;
import com.grinder.domain.comment.repository.CommentQueryRepository;
import com.grinder.domain.comment.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CommentReader {
    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;

    public Slices<Comment> getComments(Long feedId, Long parentId, Long authId, int page, int size) {
        return commentQueryRepository.getCommentSlices(feedId, parentId, authId, page, size);
    }

    public Comment getComment(Long commentId, Long authId) {
        return commentQueryRepository.getComment(commentId, authId);
    }

    public Long createComment(CreateCommentRequest request, Long memberId) {
        try {
            return commentRepository.save(request.toEntity(memberId)).getId();

        } catch (Exception e) {
            log.error("댓글 생성 중 오류 발생", e);

            throw new IllegalArgumentException("댓글 생성 중 오류 발생");
        }
    }

    public boolean deleteComment(Long commentId, Long memberId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> {
            log.error("존재하지 않는 댓글입니다. commentId: {}", commentId);

            throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
        });

        if (!commentEntity.getMemberId().equals(memberId)) {
            log.error("댓글 삭제 권한이 없습니다. commentId: {}, memberId: {}", commentId, memberId);

            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
        }

        try {
            commentRepository.deleteById(commentId);
            return true;
        } catch (Exception e) {
            log.error("댓글 삭제 중 오류 발생", e);

            return false;
        }
    }

    public boolean updateComment(Long commentId, Long authId, String content) {
        CommentEntity commentEntity = commentRepository.findById(commentId).
                orElseThrow(() -> {
                    log.error("존재하지 않는 댓글입니다. commentId: {}", commentId);

                    return new IllegalArgumentException("존재하지 않는 댓글입니다.");
                });

        if (!commentEntity.getMemberId().equals(authId)) {
            log.error("댓글 수정 권한이 없습니다. commentId: {}, memberId: {}", commentId, authId);

            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        }

        try {
            commentRepository.updateComment(commentId, content);
            return true;
        } catch (Exception e) {
            log.error("댓글 수정 중 오류 발생", e);

            return false;
        }
    }

    public boolean invisibleComment(Long commentId) {
        try {
            commentRepository.invisibleById(commentId);
            return true;
        } catch (Exception e) {
            log.error("댓글 비공개 처리 중 오류 발생", e);

            return false;
        }
    }
}

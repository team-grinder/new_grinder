package com.grinder.domain.comment.service;

import com.grinder.common.model.Slices;
import com.grinder.domain.comment.implement.CommentReader;
import com.grinder.domain.comment.model.Comment;
import com.grinder.domain.comment.model.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentReader commentReader;

    public Slices<Comment> getComments(Long feedId, Long parentId, Long authId, int page, int size) {
        return commentReader.getComments(feedId, parentId, authId, page, size);
    }

    public Comment getComment(Long commentId, Long authId) {
        try {
            return commentReader.getComment(commentId, authId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional
    public Long createComment(CreateCommentRequest request, Long authId) {
        try {
            return commentReader.createComment(request, authId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional
    public boolean deleteComment(Long commentId, Long authId) {
        try {
            commentReader.deleteComment(commentId, authId);
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional
    public boolean updateComment(Long commentId, Long authId, String content) {

        try {
            commentReader.updateComment(commentId, authId, content);
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

package com.grinder.web.systemAdmin.comment;

import com.grinder.common.model.Pages;
import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.comment.model.Comment;
import com.grinder.domain.comment.model.CommentSearchPage;
import com.grinder.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comment")
public class AdminCommentController {
    private final CommentService commentService;

    @GetMapping("/list")
    public ResponseEntity<SuccessResult<Pages<Comment>>> getCommentList(
            @ModelAttribute CommentSearchPage searchPage
    ) {
        Pages<Comment> Comments = commentService.getCommentPages(searchPage);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, Comments));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResult<Void>> deleteComment(
            @RequestParam Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResult<Void>> updateComment(
            @RequestParam Long commentId,
            @RequestBody Comment comment
    ) {
        commentService.updateComment(commentId, comment.getContent());
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PostMapping("/search")
    public ResponseEntity<SuccessResult<Comment>> createCafe(
            @RequestParam Long commentId
    ) {
        Comment comment = commentService.getComment(commentId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, comment));
    }
}

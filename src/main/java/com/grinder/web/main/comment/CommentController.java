package com.grinder.web.main.comment;

import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.Slices;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.utils.AuthenticateUtils;
import com.grinder.domain.comment.model.Comment;
import com.grinder.domain.comment.model.CreateCommentRequest;
import com.grinder.domain.comment.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<SuccessResult<Slices<Comment>>> getComments(Long feedId, Long parentId, int page, int size) {
        Long clientId = AuthenticateUtils.getAuthId();

        return ResponseEntity.ok(
                SuccessResult.of(
                        ResultEnum.SUCCESS,
                        commentService.getComments(feedId, parentId, clientId, page, size)
                )
        );
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<SuccessResult<Comment>> getComment(@PathVariable Long commentId) {
        Long clientId = AuthenticateUtils.getAuthId();
        return ResponseEntity.ok(
                SuccessResult.of(
                        ResultEnum.SUCCESS,
                        commentService.getComment(commentId, clientId)
                )
        );
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResult<Long>> createComment(@RequestBody CreateCommentRequest request) {
        Long clientId = AuthenticateUtils.getAuthId();

        return ResponseEntity.ok(
                SuccessResult.of(
                        ResultEnum.SUCCESS,
                        commentService.createComment(request, clientId)
                )
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<SuccessResult<Boolean>> deleteComment(@PathVariable Long commentId) {
        Long clientId = AuthenticateUtils.getAuthId();

        return ResponseEntity.ok(
                SuccessResult.of(
                        ResultEnum.SUCCESS,
                        commentService.deleteComment(commentId, clientId)
                )
        );
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<SuccessResult<Boolean>> updateComment(@PathVariable Long commentId, @RequestBody String content) {
        Long authId = AuthenticateUtils.getAuthId();

        return ResponseEntity.ok(
                SuccessResult.of(
                        ResultEnum.SUCCESS,
                        commentService.updateComment(commentId, authId, content)
                )
        );
    }
}

package com.grinder.web.main.like;

import com.grinder.common.utils.AuthenticateUtils;
import com.grinder.domain.like.model.ContentType;
import com.grinder.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/feed/{contentId}")
    public boolean likeFeed(@PathVariable Long contentId) {
        Long authId = AuthenticateUtils.getAuthId();

        return likeService.Like(authId, contentId, ContentType.FEED);
    }

    @DeleteMapping("/feed/{contentId}")
    public boolean unLikeFeed(@PathVariable Long contentId) {
        Long authId = AuthenticateUtils.getAuthId();

        return likeService.unLike(authId, contentId, ContentType.FEED);
    }

    @PostMapping("/comment/{contentId}")
    public boolean likeComment(@PathVariable Long contentId) {
        Long authId = AuthenticateUtils.getAuthId();

        return likeService.Like(authId, contentId, ContentType.COMMENT);
    }

    @DeleteMapping("/comment/{contentId}")
    public boolean unLikeComment(@PathVariable Long contentId) {
        Long authId = AuthenticateUtils.getAuthId();

        return likeService.unLike(authId, contentId, ContentType.COMMENT);
    }
}

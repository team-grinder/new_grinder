package com.grinder.web.systemAdmin.feed;

import com.grinder.common.model.Pages;
import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.feed.model.Feed;
import com.grinder.domain.feed.model.FeedSearchPage;
import com.grinder.domain.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/feed")
public class AdminFeedController {
    private final FeedService feedService;

    @GetMapping("/list")
    public ResponseEntity<SuccessResult<Pages<Feed>>> getFeedList(
            @ModelAttribute FeedSearchPage searchPage
    ) {
        Pages<Feed> Feeds = feedService.getFeedPages(searchPage);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, Feeds));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResult<Void>> deleteFeed(
            @RequestParam Long feedId
    ) {
        feedService.deleteFeed(feedId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResult<Void>> updateFeed(
            @RequestParam Long feedId,
            @RequestBody Feed feed
    ) {
        feedService.updateFeed(feedId, feed);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PostMapping("/search")
    public ResponseEntity<SuccessResult<Feed>> createCafe(
            @RequestParam Long feedId
    ) {
        Feed feed = feedService.getFeed(feedId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, feed));
    }
}

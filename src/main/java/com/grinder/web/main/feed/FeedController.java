package com.grinder.web.main.feed;

import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.Slices;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.utils.AuthenticateUtils;
import com.grinder.domain.feed.model.CreateFeedRequest;
import com.grinder.domain.feed.model.FeedMember;
import com.grinder.domain.feed.service.FeedService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/feed")
public class FeedController {
    private final FeedService feedService;

    @GetMapping("/cafe/{cafeId}")
    public ResponseEntity<SuccessResult<Slices<FeedMember>>> getFeedByCafeId(@PathVariable Long cafeId, int page, int size) {
        Long clientId = AuthenticateUtils.getAuthId();

        return ResponseEntity.ok(
                SuccessResult.of(
                        ResultEnum.SUCCESS,
                        feedService.getFeedCafe(cafeId, clientId, page, size)
                )
        );
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResult<Boolean>> createFeed(@ModelAttribute CreateFeedRequest request) {
        Long clientId = AuthenticateUtils.getAuthId();

        return ResponseEntity.ok(
                SuccessResult.of(
                        ResultEnum.SUCCESS,
                        feedService.createFeed(request, clientId)
                )
        );
    }
}

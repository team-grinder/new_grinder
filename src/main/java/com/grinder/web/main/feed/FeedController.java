package com.grinder.web.main.feed;

import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.Slices;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.utils.AuthenticateUtils;
import com.grinder.domain.feed.model.FeedMember;
import com.grinder.domain.feed.service.FeedService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

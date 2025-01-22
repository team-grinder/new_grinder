package com.grinder.domain.feed.implement;

import com.grinder.common.annotation.Implement;
import com.grinder.domain.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;

@Implement
@RequiredArgsConstructor
public class FeedReader {
    private final FeedRepository feedRepository;

    public void readFeedSliceByMemberId(String memberId) {

    }

    public void readFeedSliceByCafeId(String cafeId) {

    }
}

package com.grinder.domain.feed.implement;

import com.grinder.domain.feed.model.CreateFeedRequest;
import com.grinder.domain.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedReader {
    private final FeedRepository feedRepository;

    public void readFeedSliceByMemberId(String memberId) {

    }

    public void readFeedSliceByCafeId(String cafeId) {

    }

    @Transactional
    public boolean createFeed(CreateFeedRequest request) {
        try {
            feedRepository.save(request.toEntity());
        } catch (Exception e) {
            log.error("피드 생성 오류 : {}", e.getMessage());
            return false;
        }
        return true;
    }
}

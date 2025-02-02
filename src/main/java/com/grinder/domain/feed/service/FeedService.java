package com.grinder.domain.feed.service;

import com.grinder.common.model.Slices;
import com.grinder.domain.feed.implement.FeedReader;
import com.grinder.domain.feed.model.CreateFeedRequest;
import com.grinder.domain.feed.model.FeedMember;
import com.grinder.domain.image.implement.ImageReader;
import com.grinder.domain.like.model.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedService {
    private final FeedReader feedReader;
    private final ImageReader imageReader;

    public Slices<FeedMember> getFeedMember(Long memberId, Long clientId, int page, int size) {
        return feedReader.readFeedSliceByMemberId(memberId, clientId, page, size);
    }

    public Slices<FeedMember> getFeedCafe(Long cafeId, Long clientId, int page, int size) {
        return feedReader.readFeedSliceByCafeId(cafeId, clientId, page, size);
    }

    @Transactional
    public boolean createFeed(CreateFeedRequest request, Long memberId) {
        // 세션과 비교하여 VO의 memberID와 세션 memberID가 일치하는지 확인
        if (!request.getMemberId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "세션 정보와 요청 정보가 일치하지 않습니다.");
        }

        try {
            Long feedId = feedReader.createFeed(request);

            // 이미지 저장 로직 (비동기 압축)
            if (request.getImages() != null && !request.getImages().isEmpty()) {
                imageReader.createImage(request.getImages(), ContentType.FEED, feedId);
            }

            return true;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "피드 생성에 실패하였습니다.");
        }
    }
}

package com.grinder.domain.feed.implement;

import com.grinder.common.model.Slices;
import com.grinder.domain.feed.entity.FeedEntity;
import com.grinder.domain.feed.model.CreateFeedRequest;
import com.grinder.domain.feed.model.FeedMember;
import com.grinder.domain.feed.repository.FeedQueryRepository;
import com.grinder.domain.feed.repository.FeedRepository;
import com.grinder.domain.image.implement.ImageReader;
import com.grinder.domain.image.model.ImageTag;
import com.grinder.domain.tabling.implement.TablingManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedReader {
    private final FeedRepository feedRepository;
    private final FeedQueryRepository feedQueryRepository;
    private final ImageReader imageReader;
    private final TablingManager tablingManager;

    public Slices<FeedMember> readFeedSliceByMemberId(Long memberId, Long clientId, int page, int size) {
        // 피드 조회
        Slices<FeedMember> feedMemberSlices = feedQueryRepository.readFeedSliceByMemberId(memberId, clientId, page, size);

        List<Long> feedIds = feedMemberSlices.getContent().stream()
                .map(FeedMember::getFeedId).collect(Collectors.toList());

        // 이미지 조회 (피드 ID로 조회 및 S3 URL 생성)
        Map<Long, List<ImageTag>> imageTagMap = imageReader.findImageByFeedIds(feedIds);

        feedMemberSlices.getContent().forEach(feedMember -> {
            List<ImageTag> imageTag = imageTagMap.get(feedMember.getFeedId());
            if (imageTag != null && !imageTag.isEmpty()) {
                feedMember.setImageTagList(imageTag);
            }
        });

        return feedMemberSlices;
    }

    public Slices<FeedMember> readFeedSliceByCafeId(Long cafeId, Long clientId, int page, int size) {
        // 피드 조회
        Slices<FeedMember> feedMemberSlices = feedQueryRepository.readFeedSliceByCafeId(cafeId, clientId, page, size);

        List<Long> feedIds = feedMemberSlices.getContent().stream()
                .map(FeedMember::getFeedId).collect(Collectors.toList());

        // 이미지 조회 (피드 ID로 조회 및 S3 URL 생성)
        Map<Long, List<ImageTag>> imageTagMap = imageReader.findImageByFeedIds(feedIds);

        feedMemberSlices.getContent().forEach(feedMember -> {
            List<ImageTag> imageTag = imageTagMap.get(feedMember.getFeedId());
            if (imageTag != null && !imageTag.isEmpty()) {
                feedMember.setImageTagList(imageTag);
            }
        });

        return feedMemberSlices;
    }

    @Transactional
    public Long createFeed(CreateFeedRequest request) {
        try {
            FeedEntity feedEntity = feedRepository.save(request.toEntity());

            // TODO: 피드를 생성했다면 리뷰 상태 변경
            // tablingManager.updateTablingStatus(request.getBookId(), TablingStatus.COMPLETED_REVIEW);

            return feedEntity.getId();
        } catch (Exception e) {
            log.error("피드 생성 오류 : {}", e.getMessage());

            throw e;
        }
    }

    @Transactional
    public boolean deleteFeed(Long feedId, Long clientId) {
        try {
            feedRepository.deleteByIdAndMemberId(feedId, clientId);
            return true;
        } catch (Exception e) {
            log.error("피드 삭제 오류 : {}", e.getMessage());
            throw e;
        }
    }
}

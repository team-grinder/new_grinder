package com.grinder.domain.feed.implement;

import com.grinder.common.model.Slices;
import com.grinder.domain.feed.model.CreateFeedRequest;
import com.grinder.domain.feed.model.FeedMember;
import com.grinder.domain.feed.repository.FeedQueryRepository;
import com.grinder.domain.feed.repository.FeedRepository;
import com.grinder.domain.image.implement.ImageReader;
import com.grinder.domain.image.model.ImageTag;
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

    public Slices<FeedMember> readFeedSliceByMemberId(Long memberId, Long clientId, int page, int size) {
        // 피드 조회
        Slices<FeedMember> feedMemberSlices = feedQueryRepository.readFeedSliceByMemberId(memberId, clientId, page, size);

        List<Long> feedIds = feedMemberSlices.getContent().stream()
                .map(FeedMember::getFeedId).collect(Collectors.toList());

        // 이미지 조회 (피드 ID로 조회 및 S3 URL 생성)
        Map<Long, ImageTag> imageTagMap = imageReader.findImageByFeedIds(feedIds);

        feedMemberSlices.getContent().forEach(feedMember -> {
            ImageTag imageTag = imageTagMap.get(feedMember.getFeedId());
            if (imageTag != null) {
                feedMember.setImageTagList(List.of(imageTag));
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
        Map<Long, ImageTag> imageTagMap = imageReader.findImageByFeedIds(feedIds);

        feedMemberSlices.getContent().forEach(feedMember -> {
            ImageTag imageTag = imageTagMap.get(feedMember.getFeedId());
            if (imageTag != null) {
                feedMember.setImageTagList(List.of(imageTag));
            }
        });

        return feedMemberSlices;
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

package com.grinder.domain.feed.service;

import com.grinder.domain.feed.implement.FeedReader;
import com.grinder.domain.feed.model.CreateFeedRequest;
import com.grinder.domain.image.implement.ImageReader;
import com.grinder.domain.like.model.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedService {
    private final FeedReader feedReader;
    private final ImageReader imageReader;

    @Transactional
    public boolean createFeed(CreateFeedRequest request, Long memberId) {
        // 세션과 비교하여 VO의 memberID와 세션 memberID가 일치하는지 확인
        if (!request.getMemberId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "세션 정보와 요청 정보가 일치하지 않습니다.");
        }
        // 이미지 저장 로직 (비동기 압축)
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            imageReader.compressAsyncImage(request.getImages(), ContentType.FEED, request.getMemberId());
        }

        return feedReader.createFeed(request);
    }
}

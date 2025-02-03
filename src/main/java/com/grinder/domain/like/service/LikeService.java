package com.grinder.domain.like.service;

import com.grinder.domain.like.entity.LikeEntity;
import com.grinder.domain.like.model.ContentType;
import com.grinder.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public boolean Like(Long memberId, Long contentId, ContentType contentType) {
        LikeEntity likeEntity = new LikeEntity(null, memberId, contentId, contentType);

        try {
            likeRepository.save(likeEntity);
        } catch (Exception e) {
            log.info("이미 좋아요를 누른 컨텐츠입니다.");
            return false;
        }
        return true;
    }

    @Transactional
    public boolean unLike(Long memberId, Long contentId, ContentType contentType) {
        try {
            likeRepository.deleteByContentTypeAndContentIdAndMemberId(contentType, contentId, memberId);
        } catch (Exception e) {
            log.info("좋아요를 누르지 않은 컨텐츠입니다.");
            return false;
        }
        return true;
    }
}

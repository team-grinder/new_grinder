package com.grinder.domain.like.repository;

import com.grinder.domain.like.entity.LikeEntity;
import com.grinder.domain.like.model.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    void deleteByContentTypeAndContentIdAndMemberId(ContentType contentType, Long contentId, Long memberId);
}

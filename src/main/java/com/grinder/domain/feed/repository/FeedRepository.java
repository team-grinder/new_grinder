package com.grinder.domain.feed.repository;

import com.grinder.domain.feed.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
    void deleteByIdAndMemberId(Long feedId, Long memberId);
}

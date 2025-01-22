package com.grinder.domain.feed.repository;

import com.grinder.domain.feed.entity.FeedEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.grinder.domain.feed.entity.QFeedEntity.feedEntity;

@Repository
public class FeedQueryRepository {
    private final JPAQueryFactory query;

    public FeedQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public void readFeedSliceByMemberId(Long memberId, int page, int size) {
        List<FeedEntity> fetch = query.from(feedEntity)
                .select(feedEntity)
                .where(feedEntity.memberId.eq(memberId))
                .orderBy(feedEntity.createDate.desc())
                .limit(size)
                .offset((long) page * size)
                .fetch();
    }

    public void readFeedSliceByCafeId(String cafeId) {

    }
}

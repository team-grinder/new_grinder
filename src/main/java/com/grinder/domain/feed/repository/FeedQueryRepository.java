package com.grinder.domain.feed.repository;

import com.grinder.common.model.Slices;
import com.grinder.domain.feed.entity.FeedEntity;
import com.grinder.domain.feed.model.FeedMember;
import com.grinder.domain.image.entity.QImageEntity;
import com.grinder.domain.like.entity.QLikeEntity;
import com.grinder.domain.like.model.ContentType;
import com.grinder.domain.member.entity.QMemberEntity;
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

    public Slices<FeedMember> readFeedSliceByMemberId(Long memberId, int page, int size) {
        QMemberEntity member = new QMemberEntity("member");
        QLikeEntity like = new QLikeEntity("like");
        QImageEntity image = new QImageEntity("image");

        List<FeedEntity> fetch = query.from(feedEntity)
                .select(feedEntity)
                .leftJoin(member).on(feedEntity.memberId.eq(member.id))
                .leftJoin(like).on(feedEntity.id.eq(like.contentId))
                .where(feedEntity.memberId.eq(memberId)
                        .and(like.contentType.eq(ContentType.FEED).and(like.contentId.eq(feedEntity.id)))
                )
                .orderBy(feedEntity.createDate.desc())
                .limit(size)
                .offset((long) page * size)
                .fetch();

        return null;
    }

    public void readFeedSliceByCafeId(String cafeId) {

    }
}

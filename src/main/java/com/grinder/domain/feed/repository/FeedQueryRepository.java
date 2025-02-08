package com.grinder.domain.feed.repository;

import com.grinder.common.model.Slices;
import com.grinder.domain.comment.entity.QCommentEntity;
import com.grinder.domain.feed.entity.QFeedEntity;
import com.grinder.domain.feed.model.FeedMember;
import com.grinder.domain.like.entity.QLikeEntity;
import com.grinder.domain.like.model.ContentType;
import com.grinder.domain.member.entity.QMemberEntity;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class FeedQueryRepository {
    private final JPAQueryFactory query;

    public FeedQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public Slices<FeedMember> readFeedSliceByMemberId(Long memberId, Long clientId, int page, int size) {
        QFeedEntity feed = QFeedEntity.feedEntity;
        QLikeEntity like = QLikeEntity.likeEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QCommentEntity comment = QCommentEntity.commentEntity;

        NumberExpression<Long> likeCountExpression = like.id.count();

        NumberExpression<Long> commentCountExpression = comment.id.count();

        // 좋아요 눌렀는지 여부도 CASE로 변환
        Expression<Boolean> isLikedExpression = new CaseBuilder()
                .when(like.memberId.eq(clientId)).then(true)
                .otherwise(false);

        BooleanExpression isMine = feed.memberId.eq(clientId);

        List<FeedMember> fetch = query
                .select(
                        Projections.constructor(
                                FeedMember.class,
                                feed.id,
                                member.nickname,
                                member.imageUrl,
                                feed.content,
                                feed.grade,
                                feed.createDate,
                                isMine,
                                likeCountExpression, // 좋아요 총 개수
                                commentCountExpression, // 댓글 총 개수
                                isLikedExpression    // 현재 회원이 좋아요 눌렀는지 여부
                        )
                )
                .from(feed)
                .leftJoin(member).on(feed.memberId.eq(member.id))
                .leftJoin(like).on(
                        feed.id.eq(like.contentId)
                                .and(like.contentType.eq(ContentType.FEED)))
                .leftJoin(comment).on(feed.id.eq(comment.feedId))
                .where(feed.memberId.eq(memberId)) // 필요 조건
                .groupBy(feed.id, feed.memberId, member.nickname, member.imageUrl,
                        feed.content, feed.grade, feed.createDate)
                .orderBy(feed.createDate.desc())
                .limit(size + 1)
                .offset((long) page * size)
                .fetch();

        return Slices.create(fetch, page, size);
    }

    public Slices<FeedMember> readFeedSliceByCafeId(Long cafeId, Long clientId, int page, int size) {
        QFeedEntity feed = QFeedEntity.feedEntity;
        QLikeEntity like = QLikeEntity.likeEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QCommentEntity comment = QCommentEntity.commentEntity;

        NumberExpression<Long> likeCountExpression = like.id.count();

        NumberExpression<Long> commentCountExpression = comment.id.count();

        NumberExpression<Integer> isLikedAgg = new CaseBuilder()
                .when(like.memberId.eq(clientId)).then(1)
                .otherwise(0)
                .max();

        Expression<Boolean> isLikedExpression =
                isLikedAgg.when(1).then(true).otherwise(false);

        BooleanExpression isMine = feed.memberId.eq(clientId);

        List<FeedMember> fetch = query
                .select(
                        Projections.constructor(FeedMember.class,
                                feed.id,
                                member.nickname,
                                member.imageUrl,
                                feed.content,
                                feed.grade,
                                feed.createDate,
                                isMine,
                                likeCountExpression, // 좋아요 총 개수
                                commentCountExpression, // 댓글 총 개수
                                isLikedExpression    // 현재 회원이 좋아요 눌렀는지 여부
                        )
                )
                .from(feed)
                .leftJoin(member).on(feed.memberId.eq(member.id))
                .leftJoin(like).on(
                        feed.id.eq(like.contentId)
                                .and(like.contentType.eq(ContentType.FEED)))
                .leftJoin(comment).on(feed.id.eq(comment.feedId))
                .where(feed.cafeId.eq(cafeId))
                .groupBy(feed.id, feed.memberId, member.nickname, member.imageUrl,
                        feed.content, feed.grade, feed.createDate)
                .orderBy(feed.createDate.desc())
                .limit(size + 1)
                .offset((long) page * size)
                .fetch();

        return Slices.create(fetch, page, size);
    }
}

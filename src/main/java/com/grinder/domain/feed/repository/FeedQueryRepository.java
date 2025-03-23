package com.grinder.domain.feed.repository;

import com.grinder.common.model.Pages;
import com.grinder.common.model.Slices;
import com.grinder.common.utils.DateUtils;
import com.grinder.domain.comment.entity.QCommentEntity;
import com.grinder.domain.feed.entity.FeedEntity;
import com.grinder.domain.feed.entity.QFeedEntity;
import com.grinder.domain.feed.model.Feed;
import com.grinder.domain.feed.model.FeedMember;
import com.grinder.domain.feed.model.FeedSearchPage;
import com.grinder.domain.like.entity.QLikeEntity;
import com.grinder.domain.like.model.ContentType;
import com.grinder.domain.member.entity.QMemberEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.grinder.domain.feed.entity.QFeedEntity.feedEntity;

@Repository
public class FeedQueryRepository {
    private final JPAQueryFactory query;

    public FeedQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public Slices<FeedMember> readFeedSliceByClientId(Long clientId, int page, int size) {
        QFeedEntity feed = feedEntity;
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
                .where(feed.memberId.eq(clientId)) // 필요 조건
                .groupBy(feed.id, feed.memberId, member.nickname, member.imageUrl,
                        feed.content, feed.grade, feed.createDate)
                .orderBy(feed.createDate.desc())
                .limit(size + 1)
                .offset((long) page * size)
                .fetch();

        return Slices.create(fetch, page, size);
    }

    public Slices<FeedMember> readFeedSliceByCafeId(Long cafeId, Long clientId, int page, int size) {
        QFeedEntity feed = feedEntity;
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

    public Pages<Feed> getFeedPages(FeedSearchPage searchPage) {
        // 날짜가 null이 아니라면 startDate와 endDate를 기준으로 검색
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 날짜 범위 검색
        searchForDateRange(searchPage, booleanBuilder);

        // 검색 타입 및 검색어 검색
        SearchForType(searchPage, booleanBuilder);

        Long feedCount = query.from(feedEntity)
                .select(feedEntity.count())
                .where(booleanBuilder)
                .fetchOne();

        List<Feed> feedEntities = query.from(feedEntity)
                .select(feedEntity)
                .where(booleanBuilder)
                .orderBy(feedEntity.createDate.desc())
                .offset(searchPage.getPage() * searchPage.getSize())
                .limit(searchPage.getSize())
                .fetch().stream().map(FeedEntity::toFeed).collect(Collectors.toList());

        return Pages.create(
                feedEntities,
                feedCount != null ? feedCount : 0,
                searchPage.getPage(),
                searchPage.getSize(),
                5
        );
    }

    private void SearchForType(FeedSearchPage searchPage, BooleanBuilder booleanBuilder) {
        if (searchPage.getSearchQuery() == null || searchPage.getSearchQuery().isBlank()) return; // 검색 타입이 null인 경우는 검색하지 않음

        switch (searchPage.getSearchType()) {
            case ALL:
                if (searchPage.getSearchQuery().matches("\\d+")) {
                    booleanBuilder.and(feedEntity.memberId.eq(Long.parseLong(searchPage.getSearchQuery()))
                            .or(feedEntity.content.contains(searchPage.getSearchQuery())));
                } else {
                    booleanBuilder.and(feedEntity.content.contains(searchPage.getSearchQuery()));
                }
                break;
            case MEMBER_ID:
                if (searchPage.getSearchQuery().matches("\\d+")) {
                    booleanBuilder.and(feedEntity.memberId.eq(Long.parseLong(searchPage.getSearchQuery())));
                }
                break;
            case CONTENT:
                booleanBuilder.and(feedEntity.content.contains(searchPage.getSearchQuery()));
                break;
            default:
                throw new IllegalArgumentException("존재하지 않는 검색 방식입니다.");
        }
    }

    private void searchForDateRange(FeedSearchPage searchPage, BooleanBuilder booleanBuilder) {
        if (searchPage.getStartDate() != null && searchPage.getEndDate() != null) {
            LocalDateTime[] betweenRange = DateUtils.parseForStartAndEndDate(searchPage.getStartDate(), searchPage.getEndDate());
            booleanBuilder.and(feedEntity.createDate.between(betweenRange[0], betweenRange[1]));
        }
    }
}

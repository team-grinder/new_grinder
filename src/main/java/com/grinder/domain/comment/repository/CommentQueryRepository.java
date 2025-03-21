package com.grinder.domain.comment.repository;

import com.grinder.common.model.Pages;
import com.grinder.common.model.Slices;
import com.grinder.common.utils.DateUtils;
import com.grinder.domain.comment.entity.CommentEntity;
import com.grinder.domain.comment.entity.QCommentEntity;
import com.grinder.domain.comment.model.Comment;
import com.grinder.domain.comment.model.CommentSearchPage;
import com.grinder.domain.member.entity.QMemberEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.grinder.domain.comment.entity.QCommentEntity.commentEntity;

@Repository
public class CommentQueryRepository {
    private final JPAQueryFactory query;

    public CommentQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public Pages<Comment> getCommentPages(CommentSearchPage searchPage) {
        // 날짜가 null이 아니라면 startDate와 endDate를 기준으로 검색
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 날짜 범위 검색
        searchForDateRange(searchPage, booleanBuilder);

        // 검색 타입 및 검색어 검색
        SearchForType(searchPage, booleanBuilder);

        Long commentCount = query.from(commentEntity)
                .select(commentEntity.count())
                .where(booleanBuilder)
                .fetchOne();

        // 페이지네이션 처리
        List<Comment> commentEntities = query.from(commentEntity)
                .select(commentEntity)
                .where(booleanBuilder)
                .offset(searchPage.getPage() * searchPage.getSize())
                .limit(searchPage.getSize())
                .fetch().stream()
                .map(CommentEntity::toComment)
                .collect(Collectors.toList());

        return Pages.create(
                commentEntities,
                commentCount == null ? 0 : commentCount,
                searchPage.getPage(),
                searchPage.getSize(),
                5
        );
    }

    public Slices<Comment> getCommentSlices(Long feedId, Long parentCommentId, Long authId, int page, int size) {
        QCommentEntity comment = commentEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QCommentEntity commentSub = new QCommentEntity("commentSub");

        BooleanExpression parentCommentExpression = parentCommentId == null ?
                comment.parentCommentId.isNull() : comment.parentCommentId.eq(parentCommentId);

        JPQLQuery<Long> replyCountExpression = parentCommentId == null
                ? JPAExpressions.select(commentSub.id.count())
                .from(commentSub)
                .where(commentSub.parentCommentId.eq(comment.id))
                : JPAExpressions.select(Expressions.numberTemplate(Long.class, "0"));

        BooleanExpression isMine = comment.memberId.eq(authId);

        List<Comment> commentList = query
                .select(Projections.constructor(
                        Comment.class,
                        comment.id,
                        comment.feedId,
                        comment.memberId,
                        member.nickname,
                        comment.content,
                        comment.parentCommentId,
                        comment.createDate,
                        replyCountExpression,
                        isMine
                ))
                .from(comment)
                .leftJoin(member).on(comment.memberId.eq(member.id))
                .where(comment.feedId.eq(feedId).and(parentCommentExpression))
                .orderBy(comment.createDate.desc())
                .limit(size + 1)
                .offset((long) page * size)
                .fetch();

        return Slices.create(commentList, page, size);
    }

    public Comment getComment(Long commentId, Long authId) {
        QCommentEntity comment = commentEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        return query
                .select(Projections.constructor(
                        Comment.class,
                        comment.id,
                        comment.feedId,
                        comment.memberId,
                        member.nickname,
                        comment.content,
                        comment.parentCommentId,
                        comment.createDate,
                        Expressions.constant(0L),
                        comment.memberId.eq(authId)
                ))
                .from(comment)
                .leftJoin(member).on(comment.memberId.eq(member.id))
                .where(comment.id.eq(commentId))
                .fetchOne();
    }

    private void SearchForType(CommentSearchPage searchPage, BooleanBuilder booleanBuilder) {
        if (searchPage.getSearchType() == null) return; // 검색 타입이 null인 경우는 검색하지 않음

        switch (searchPage.getSearchType()) {
            case ALL:
                booleanBuilder.and(commentEntity.memberId.eq(Long.parseLong(searchPage.getSearchQuery()))
                        .or(commentEntity.content.contains(searchPage.getSearchQuery())));
                break;
            case MEMBER_ID:
                booleanBuilder.and(commentEntity.memberId.eq(Long.parseLong(searchPage.getSearchQuery())));
                break;
            case CONTENT:
                booleanBuilder.and(commentEntity.content.contains(searchPage.getSearchQuery()));
                break;
            default:
                throw new IllegalArgumentException("존재하지 않는 검색 방식입니다.");
        }
    }

    private void searchForDateRange(CommentSearchPage searchPage, BooleanBuilder booleanBuilder) {
        if (searchPage.getStartDate() != null && searchPage.getEndDate() != null) {
            LocalDateTime[] betweenRange = DateUtils.parseForStartAndEndDate(searchPage.getStartDate(), searchPage.getEndDate());
            booleanBuilder.and(commentEntity.createDate.between(betweenRange[0], betweenRange[1]));
        }
    }
}
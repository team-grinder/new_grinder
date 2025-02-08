package com.grinder.domain.comment.repository;

import com.grinder.common.model.Slices;
import com.grinder.domain.comment.entity.QCommentEntity;
import com.grinder.domain.comment.model.Comment;
import com.grinder.domain.member.entity.QMemberEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CommentQueryRepository {
    private final JPAQueryFactory query;

    public CommentQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public Slices<Comment> getCommentSlices(Long feedId, Long parentCommentId, Long authId, int page, int size) {
        QCommentEntity comment = QCommentEntity.commentEntity;
        QMemberEntity member = QMemberEntity.memberEntity;

        BooleanExpression parentCommentExpression = parentCommentId == null ?
                comment.parentCommentId.isNull() : comment.parentCommentId.eq(parentCommentId);

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
        QCommentEntity comment = QCommentEntity.commentEntity;
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
                        comment.memberId.eq(authId)
                ))
                .from(comment)
                .leftJoin(member).on(comment.memberId.eq(member.id))
                .where(comment.id.eq(commentId))
                .fetchOne();
    }
}
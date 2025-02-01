package com.grinder.domain.comment.repository;

import com.grinder.domain.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Modifying
    @Query("update CommentEntity c set c.content = :content where c.id = :commentId")
    void updateComment(Long commentId, String content);

    @Modifying
    @Query("update CommentEntity c set c.isVisible = false where c.id = :commentId")
    void invisibleById(Long commentId);
}

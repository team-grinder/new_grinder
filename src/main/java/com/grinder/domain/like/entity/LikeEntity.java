package com.grinder.domain.like.entity;

import com.grinder.domain.like.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "idx_content_type", columnList = "content_type")},
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_member_id_content_id", columnNames = {"memberId", "contentId"})
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class LikeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

    @Column(nullable = false)
    private Long contentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false, length = 16)
    private ContentType contentType;
}
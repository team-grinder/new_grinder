package com.grinder.domain.feed.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.feed.model.Feed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedEntity extends BaseDateEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

    private Long cafeId;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    private Boolean isVisible;

    private Integer grade;

    @Column(name = "rank_value")
    private Integer rank;

    public FeedEntity(Long memberId, Long cafeId, String content, Integer grade, Integer rank, Boolean isVisible) {
        this.memberId = memberId;
        this.cafeId = cafeId;
        this.content = content;
        this.grade = grade;
        this.rank = rank;
        this.isVisible = isVisible;
    }

    public Feed toFeed() {
        return Feed.builder()
                .id(id)
                .memberId(memberId)
                .cafeId(cafeId)
                .content(content)
                .grade(grade)
                .rank(rank)
                .isVisible(isVisible)
                .build();
    }

    public void updateFeed(Feed feed) {
        Optional.ofNullable(feed.getContent()).ifPresent(content -> this.content = content);
        Optional.ofNullable(feed.getGrade()).ifPresent(grade -> this.grade = grade);
        Optional.ofNullable(feed.getRank()).ifPresent(rank -> this.rank = rank);
        Optional.ofNullable(feed.getIsVisible()).ifPresent(isVisible -> this.isVisible = isVisible);
    }
}
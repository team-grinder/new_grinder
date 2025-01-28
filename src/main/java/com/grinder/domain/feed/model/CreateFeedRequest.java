package com.grinder.domain.feed.model;

import com.grinder.common.annotation.Name;
import com.grinder.domain.feed.entity.FeedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeedRequest {
    private Long memberId;

    private Long cafeId;

    private String content;

    private Integer grade;

    private Integer rank;

    @Name(defaultValue = "true", description = "유저가 생성하는 피드는 기본적으로 true를 반환. ")
    private Boolean isVisible = true;

    public void changeVisible() {
        this.isVisible = !this.isVisible;
    }

    public FeedEntity toEntity() {
        return new FeedEntity(memberId, cafeId, content, grade, rank, isVisible);
    }
}

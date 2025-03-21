package com.grinder.domain.feed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feed {

    private Long id;

    private String title;

    private String content;

    private String imageUrl;

    private String createdAt;

    private String updatedAt;

    private Long cafeId;

    private Long memberId;

    private Boolean isVisible;

    private Integer grade;

    private Integer rank;
}

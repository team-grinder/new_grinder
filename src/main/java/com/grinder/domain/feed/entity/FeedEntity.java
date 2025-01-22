package com.grinder.domain.feed.entity;

import com.grinder.common.entity.BaseDateEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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

}
package com.grinder.domain.feed.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.cafe.entity.Cafe;
import com.grinder.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "idx_cafe_id", columnList = "cafe_id")
})
@NoArgsConstructor
@AllArgsConstructor
public class Feed extends BaseDateEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    private Boolean isVisible;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "rank")
    private Integer rank;

}
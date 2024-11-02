package com.grinder.domain.seller.apply.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.cafe.entity.Cafe;
import com.grinder.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SellerApply extends BaseDateEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cafe cafe;

    private String regImageUrl;

}
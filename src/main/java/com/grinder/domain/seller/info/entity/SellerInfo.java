package com.grinder.domain.seller.info.entity;

import com.grinder.domain.cafe.entity.Cafe;
import com.grinder.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "cafe_id"})
})
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfo {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Cafe cafe;
}
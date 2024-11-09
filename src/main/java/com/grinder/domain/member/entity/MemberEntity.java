package com.grinder.domain.member.entity;

import com.grinder.common.annotation.Name;
import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.member.model.MemberBasicInfo;
import com.grinder.domain.member.model.TierType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class MemberEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private TierType tier;

    @Name(name = "결제정보 연관 관계")
    private Long PaymentInfoId;

    @Name(name = "카페 관리자 연관 관계")
    private Long CafeAdminId;

    @Name(name = "삭제 여부")
    private boolean isDeleted;

    public MemberBasicInfo toBasicInfo() {
        return MemberBasicInfo.builder()
                .id(id)
                .email(email)
                .phoneNumber(phoneNumber)
                .tier(tier)
                .build();
    }
}

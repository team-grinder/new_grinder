package com.grinder.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Member {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private String phoneNumber;
    private LoginType loginType;
    private TierType tier;
    private String imageUrl;
    private Long PaymentInfoId;
    private String CafeAdminId;
    private boolean isDeleted;
}
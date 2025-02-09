package com.grinder.domain.member.model;

import com.grinder.domain.member.entity.CafeAdminInfoEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeAdminInfo {
    private String cafeAdminId;
    private String businessNumber;
    private String businessName;
    private String businessAddress;
    private String businessContact;

    public static CafeAdminInfo from(CafeAdminInfoEntity entity) {
        return CafeAdminInfo.builder()
                .cafeAdminId(entity.getCafeAdminId())
                .businessNumber(entity.getBusinessNumber())
                .businessName(entity.getBusinessName())
                .businessAddress(entity.getBusinessAddress())
                .businessContact(entity.getBusinessContact())
                .build();
    }
}

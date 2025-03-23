package com.grinder.domain.member.model;

import com.grinder.domain.member.entity.CafeAdminMappingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CafeAdminMapping {
    private Long id;
    private String cafeAdminId;
    private Long cafeId;
    private AdminRole role;

    public static CafeAdminMapping from(CafeAdminMappingEntity entity) {
        return CafeAdminMapping.builder()
                .id(entity.getId())
                .cafeAdminId(entity.getCafeAdminId())
                .cafeId(entity.getCafeId())
                .role(entity.getRole())
                .build();
    }
}

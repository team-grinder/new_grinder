package com.grinder.domain.member.implement;

import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.domain.member.entity.CafeAdminInfoEntity;
import com.grinder.domain.member.entity.CafeAdminMappingEntity;
import com.grinder.domain.member.model.AdminRole;
import com.grinder.domain.member.model.CafeAdminInfoRegister;
import com.grinder.domain.member.model.CafeAdminMapping;
import com.grinder.domain.member.repository.CafeAdminInfoRepository;
import com.grinder.domain.member.repository.CafeAdminMappingRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@RequiredArgsConstructor
public class CafeAdminMappingManager {
    private final CafeAdminMappingRepository cafeAdminMappingRepository;
    private final CafeAdminInfoRepository cafeAdminInfoRepository;
    @Transactional
    public CafeAdminMapping assignCafeToManager(String cafeAdminId, Long cafeId, AdminRole role, CafeAdminInfoRegister register) {
        if (cafeAdminMappingRepository.existsByCafeAdminIdAndCafeId(cafeAdminId, cafeId)) {
            throw new MemberException(AuthResultEnum.CAFE_ALREADY_MANAGED);
        }

        CafeAdminMappingEntity mapping = CafeAdminMappingEntity.builder()
                .cafeAdminId(cafeAdminId)
                .cafeId(cafeId)
                .role(role)
                .build();

        CafeAdminInfoEntity managerInfo = CafeAdminInfoEntity.builder()
                .cafeAdminId(cafeAdminId)
                .businessNumber(register.getBusinessNumber())
                .businessName(register.getBusinessName())
                .businessAddress(register.getBusinessAddress())
                .businessContact(register.getBusinessContact())
                .build();
        cafeAdminInfoRepository.save(managerInfo);

        return CafeAdminMapping.from(cafeAdminMappingRepository.save(mapping));
    }

    @Transactional
    public void removeCafeFromManager(String cafeAdminId, Long cafeId) {
        cafeAdminMappingRepository.deleteByCafeAdminIdAndCafeId(cafeAdminId, cafeId);
    }

    @Transactional
    public void removeAllCafesFromManager(String cafeAdminId) {
        cafeAdminMappingRepository.deleteByCafeAdminId(cafeAdminId);
    }

    @Transactional
    public void updateManagerRole(String cafeAdminId, Long cafeId, AdminRole role) {
        CafeAdminMappingEntity mapping = cafeAdminMappingRepository
                .findByCafeAdminIdAndCafeId(cafeAdminId, cafeId)
                .orElseThrow(() -> new MemberException(AuthResultEnum.CAFE_MANAGER_NOT_FOUND));

        cafeAdminMappingRepository.delete(mapping);

        CafeAdminMappingEntity newMapping = CafeAdminMappingEntity.builder()
                .cafeAdminId(cafeAdminId)
                .cafeId(cafeId)
                .role(role)
                .build();

        cafeAdminMappingRepository.save(newMapping);
    }

    public List<Long> getManagedCafeIds(String cafeAdminId) {
        if (cafeAdminId == null) {
            return Collections.emptyList();
        }

        return cafeAdminMappingRepository.findByCafeAdminId(cafeAdminId)
                .stream()
                .map(CafeAdminMappingEntity::getCafeId)
                .collect(Collectors.toList());
    }

    public boolean isManager(String cafeAdminId, Long cafeId) {
        return cafeAdminMappingRepository.existsByCafeAdminIdAndCafeId(cafeAdminId, cafeId);
    }
}

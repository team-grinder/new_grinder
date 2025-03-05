package com.grinder.domain.member.implement;

import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.domain.member.entity.CafeAdminMappingEntity;
import com.grinder.domain.member.model.AdminRole;
import com.grinder.domain.member.model.CafeAdminMapping;
import com.grinder.domain.member.repository.CafeAdminMappingRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@RequiredArgsConstructor
public class CafeAdminMappingManager {
    CafeAdminMappingRepository cafeAdminMappingRepository;

    @Transactional
    public CafeAdminMapping assignCafeToManager(String cafeAdminId, Long cafeId, AdminRole role) {
        if (cafeAdminMappingRepository.existsByCafeAdminIdAndCafeId(cafeAdminId, cafeId)) {
            throw new MemberException(AuthResultEnum.CAFE_ALREADY_MANAGED);
        }

        CafeAdminMappingEntity mapping = CafeAdminMappingEntity.builder()
                .cafeAdminId(cafeAdminId)
                .cafeId(cafeId)
                .role(role)
                .build();

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
        return cafeAdminMappingRepository.findByCafeAdminId(cafeAdminId)
                .stream()
                .map(CafeAdminMappingEntity::getCafeId)
                .collect(Collectors.toList());
    }

    public List<String> getCafeManagerIds(Long cafeId) {
        return cafeAdminMappingRepository.findByCafeId(cafeId)
                .stream()
                .map(CafeAdminMappingEntity::getCafeAdminId)
                .collect(Collectors.toList());
    }

    public boolean isManager(String cafeAdminId, Long cafeId) {
        return cafeAdminMappingRepository.existsByCafeAdminIdAndCafeId(cafeAdminId, cafeId);
    }

    public List<CafeAdminMapping> getMappingsForCafeAdmin(String cafeAdminId) {
        return cafeAdminMappingRepository.findByCafeAdminId(cafeAdminId)
                .stream()
                .map(CafeAdminMapping::from)
                .collect(Collectors.toList());
    }
}

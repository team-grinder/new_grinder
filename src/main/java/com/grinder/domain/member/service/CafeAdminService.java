package com.grinder.domain.member.service;

import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.domain.cafe.implement.CafeReader;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.member.entity.CafeAdminInfoEntity;
import com.grinder.domain.member.implement.CafeAdminManager;
import com.grinder.domain.member.implement.CafeAdminMappingManager;
import com.grinder.domain.member.implement.MemberManager;
import com.grinder.domain.member.model.AdminRole;
import com.grinder.domain.member.model.CafeAdminInfo;
import com.grinder.domain.member.model.CafeAdminInfoRegister;
import com.grinder.domain.member.model.CafeAdminMapping;
import com.grinder.domain.member.model.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

@Transactional(readOnly = true)
public class CafeAdminService {
    private final CafeAdminManager cafeAdminManager;
    private final MemberManager memberManager;
    private final CafeAdminMappingManager cafeAdminMappingManager;
    private final CafeReader cafeManager;
    @Transactional
    public CafeAdminMapping assignCafeToAdmin(Long memberId, Long cafeId, AdminRole role,CafeAdminInfoRegister register) {
        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId() == null) {
            member.setCafeAdminId(UUID.randomUUID().toString());
        }
        memberManager.updateCafeAdminId(memberId,member.getCafeAdminId());

        if(cafeManager.read(cafeId).getId()==null) {
            throw new MemberException(AuthResultEnum.CAFE_NOT_FOUND);
        }

        return cafeAdminMappingManager.assignCafeToManager(member.getCafeAdminId(), cafeId, role, register);
    }

    public List<CafeAdminInfo> getCafeAdminInfoByMemberId(Long memberId){
        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId()==null){
            throw new MemberException(AuthResultEnum.NO_ADMIN_PERMISSION);
        }
        List<Long> cafeIds = this.cafeAdminMappingManager.getManagedCafeIds(member.getCafeAdminId());

        if(cafeIds.isEmpty()) {
            CafeAdminInfoEntity adminInfo = cafeAdminManager.getCafeAdminInfo(member.getCafeAdminId());
            return Collections.singletonList(CafeAdminInfo.from(adminInfo));
        }

        List<CafeAdminInfo> results = new ArrayList<>();

        CafeAdminInfoEntity defaultInfo = cafeAdminManager.getCafeAdminInfo(member.getCafeAdminId());
        CafeAdminInfo defaultAdminInfo = CafeAdminInfo.from(defaultInfo);

        for(Long cafeId : cafeIds) {
            CafeAdminInfo cafeAdminInfo = CafeAdminInfo.builder()
                    .cafeId(cafeId)
                    .cafeAdminId(defaultAdminInfo.getCafeAdminId())
                    .businessNumber(defaultAdminInfo.getBusinessNumber())
                    .businessName(defaultAdminInfo.getBusinessName())
                    .businessAddress(defaultAdminInfo.getBusinessAddress())
                    .businessContact(defaultAdminInfo.getBusinessContact())
                    .build();

            results.add(cafeAdminInfo);
        }

        return results;
    }

    @Transactional
    public void updateCafeManagerInfo(Long memberId, CafeAdminInfoRegister request) {
        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId()==null){
            throw new MemberException(AuthResultEnum.NO_ADMIN_PERMISSION);
        }

        cafeAdminManager.updateCafeAdminInfo(member.getCafeAdminId(),request);
    }

    @Transactional
    public void deleteCafeAdminInfo(Long memberId){

        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId()==null){
            throw new MemberException(AuthResultEnum.NO_ADMIN_PERMISSION);
        }
        cafeAdminManager.deleteCafeAdminInfo(member.getCafeAdminId());
        memberManager.revokeCafeAdminId(memberId);
    }

    @Transactional
    public void removeCafeFromAdmin(Long memberId, Long cafeId) {
        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId() == null) {
            throw new MemberException(AuthResultEnum.NO_ADMIN_PERMISSION);
        }

        cafeAdminMappingManager.removeCafeFromManager(member.getCafeAdminId(), cafeId);
    }

    public List<Cafe> getManagedCafes(Long memberId) {
        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId() == null) {
            throw new MemberException(AuthResultEnum.NO_ADMIN_PERMISSION);
        }

        List<Long> cafeIds = cafeAdminMappingManager.getManagedCafeIds(member.getCafeAdminId());
        return cafeIds.stream()
                .map(cafeManager::read)
                .collect(Collectors.toList());
    }

    public boolean canManageCafe(Long memberId, Long cafeId) {
        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId() == null) {
            return false;
        }

        return cafeAdminMappingManager.isManager(member.getCafeAdminId(), cafeId);
    }

}

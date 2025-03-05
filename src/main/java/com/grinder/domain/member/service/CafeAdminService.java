package com.grinder.domain.member.service;

import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.domain.cafe.implement.CafeManager;
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
    public void authorizeToCafeAdmin(Long memberId, CafeAdminInfoRegister register){

        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId()==null){
            member.setCafeAdminId(UUID.randomUUID().toString()); //TODO: 부여 방식 및 분리 고려
        }

        cafeAdminManager.createCafeAdminInfo(member.getCafeAdminId(), register);
        memberManager.updateCafeAdminId(memberId,member.getCafeAdminId());
    }

    public CafeAdminInfo getCafeAdminInfoByMemberId(Long memberId){
        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId()==null){
            throw new MemberException(AuthResultEnum.NO_ADMIN_PERMISSION);
        }

        CafeAdminInfoEntity adminInfo = cafeAdminManager.getCafeAdminInfo(member.getCafeAdminId());
        return CafeAdminInfo.from(adminInfo);
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
    public CafeAdminMapping assignCafeToAdmin(Long memberId, Long cafeId, AdminRole role) {
        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId() == null) {
            throw new MemberException(AuthResultEnum.NO_ADMIN_PERMISSION);
        }

        if(cafeManager.read(cafeId).getId()==null) {
            throw new MemberException(AuthResultEnum.CAFE_NOT_FOUND);
        }

        return cafeAdminMappingManager.assignCafeToManager(member.getCafeAdminId(), cafeId, role);
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

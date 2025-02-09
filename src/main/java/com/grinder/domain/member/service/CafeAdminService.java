package com.grinder.domain.member.service;

import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.domain.member.entity.CafeAdminInfoEntity;
import com.grinder.domain.member.implement.CafeAdminManager;
import com.grinder.domain.member.implement.MemberManager;
import com.grinder.domain.member.model.CafeAdminInfo;
import com.grinder.domain.member.model.CafeAdminInfoRegister;
import com.grinder.domain.member.model.Member;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeAdminService {
    private final CafeAdminManager cafeAdminManager;
    private final MemberManager memberManager;
    @Transactional
    public void authorizeToCafeAdmin(Long memberId, CafeAdminInfoRegister register){

        Member member = memberManager.readById(memberId);
        if(member.getCafeAdminId()==null){
            member.setCafeAdminId(UUID.randomUUID().toString()); //TODO: 부여 방식 및 분리 고려
        }

        cafeAdminManager.createCafeAdminInfo(member.getCafeAdminId(), register);
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

}

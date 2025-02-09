package com.grinder.domain.member.implement;

import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.domain.member.entity.CafeAdminInfoEntity;
import com.grinder.domain.member.model.CafeAdminInfoRegister;
import com.grinder.domain.member.repository.CafeAdminInfoRepository;
import com.grinder.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CafeAdminManager {
    private final MemberRepository memberRepository;
    private final CafeAdminInfoRepository cafeAdminInfoRepository;

    @Transactional
    public CafeAdminInfoEntity createCafeAdminInfo(String cafeAdminId, CafeAdminInfoRegister register){

        CafeAdminInfoEntity managerInfo = CafeAdminInfoEntity.builder()
                .cafeAdminId(cafeAdminId)
                .businessNumber(register.getBusinessNumber())
                .businessName(register.getBusinessName())
                .businessAddress(register.getBusinessAddress())
                .businessContact(register.getBusinessContact())
                .build();

        return cafeAdminInfoRepository.save(managerInfo);
    }

    public CafeAdminInfoEntity getCafeAdminInfo(String cafeAdminId) {
        return cafeAdminInfoRepository.findById(cafeAdminId)
                .orElseThrow(() -> new MemberException(AuthResultEnum.BUSINESS_NUMBER_NOT_FOUND));
    }

    public void updateCafeAdminInfo(String cafeAdminId, CafeAdminInfoRegister register){
        CafeAdminInfoEntity cafeAdminInfo = getCafeAdminInfo(cafeAdminId);
        cafeAdminInfo.updateCafeAdminInfo(register);
        cafeAdminInfoRepository.save(cafeAdminInfo);
    }

    public void deleteCafeAdminInfo(String cafeAdminId){
        cafeAdminInfoRepository.deleteById(cafeAdminId);
    }
}

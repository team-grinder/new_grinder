package com.grinder.domain.member.implement;

import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.domain.member.entity.MemberEntity;
import com.grinder.domain.member.model.LoginType;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.model.MemberBasicInfo;
import com.grinder.domain.member.model.TierType;
import com.grinder.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberManager {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberBasicInfo read(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new MemberException(AuthResultEnum.MEMBER_NOT_FOUND)
        ).toBasicInfo();
    }
    public MemberBasicInfo readEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new MemberException(AuthResultEnum.MEMBER_NOT_FOUND)
        ).toBasicInfo();
    }

    public Member save(String email, String password, String nickname) {
        MemberEntity memberEntity = MemberEntity.commonBuilder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .loginType(LoginType.COMMON)
                .tier(TierType.SILVER)
                .isDeleted(false)
                .build();

        return memberRepository.save(memberEntity).toMember();
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberEntity::toMember);
    }

    public boolean validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(AuthResultEnum.DUPLICATE_EMAIL);
        } else return false;
    }

    public boolean validateDuplicateNickname(String nickname){
        if(memberRepository.existsByNickname(nickname)){
            throw new MemberException(AuthResultEnum.DUPLICATE_NICKNAME);
        } else return false;
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}

package com.grinder.domain.member.implement;

import com.grinder.common.exception.MemberException;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepository memberRepository;

    public Member readByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new MemberException("해당 이메일로 가입된 회원이 없습니다.")
        ).toMember();
    }

    public Long readIdByEmail(String email) {
        return memberRepository.findIdByEmail(email).orElseThrow(
                () -> new MemberException("해당 이메일로 가입된 회원이 없습니다.")
        );
    }
}

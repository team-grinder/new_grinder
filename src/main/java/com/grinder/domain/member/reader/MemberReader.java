package com.grinder.domain.member.reader;

import com.grinder.domain.member.model.MemberBasicInfo;
import com.grinder.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepository memberRepository;

    public MemberBasicInfo read(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 회원이 존재하지 않습니다.")
        ).toBasicInfo();
    }

    public MemberBasicInfo readEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 이메일로 가입된 회원이 없습니다.")
        ).toBasicInfo();
    }
}

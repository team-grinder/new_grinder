package com.grinder.common.security;

import com.grinder.common.security.model.MemberUserDetails;
import com.grinder.domain.member.entity.MemberEntity;
import com.grinder.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email){
        MemberEntity memberEntity = memberRepository.findByEmail(email)
                .orElseThrow(()->new NullPointerException("회원 정보가 존재하지 않습니다. : " + email));

        return new MemberUserDetails(memberEntity);
    }
}
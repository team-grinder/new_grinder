package com.grinder.common.security.common.service;

import com.grinder.common.security.common.model.MemberUserDetails;
import com.grinder.domain.member.entity.MemberEntity;
import com.grinder.domain.member.implement.LoginAttemptManager;
import com.grinder.domain.member.implement.LoginHistoryManager;
import com.grinder.domain.member.model.LoginType;
import com.grinder.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final LoginAttemptManager loginAttemptManager;
    private final LoginHistoryManager loginHistoryManager;

    /**
     *로그인 잠금상태 등 고려
     * 사용자 조회 후 로그인 이력 저장
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        loginAttemptManager.validateLoginAttempt(email);

        MemberEntity memberEntity = memberRepository.findByEmailAndLoginType(email, LoginType.COMMON)
                .filter(member -> !member.isDeleted())
                .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 존재하지 않습니다: " + email));

        loginHistoryManager.saveLoginHistory(memberEntity.getId());

        return new MemberUserDetails(memberEntity);
    }
    public void handleLoginSuccess(String email) {
        loginAttemptManager.handleLoginSuccess(email);
    }

    public void handleLoginFailure(String email) {
        loginAttemptManager.handleLoginFailure(email);
    }

}
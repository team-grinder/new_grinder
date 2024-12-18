package com.grinder.domain.member.implement;

import com.grinder.common.exception.LoginException;
import com.grinder.common.exception.MemberException;
import com.grinder.domain.member.entity.LoginAttemptEntity;
import com.grinder.domain.member.entity.MemberEntity;
import com.grinder.domain.member.model.TierType;
import com.grinder.domain.member.repository.MemberRepository;
import com.grinder.domain.member.repository.login.LoginAttemptRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
/**
 * 1. 로그인 시도기록 조회 -> 없으면 생성,잠기면 예외 발생
 * 2. 로그인 성공 시 실패 카운트 초기화
 * 3. 로그인 실패 시 카운트 증가, 카운트 초과면 잠금처리
 */
public class LoginAttemptManager {
    private final LoginAttemptRepository loginAttemptRepository;
    private final MemberRepository memberRepository;
    private static final int MAX_ATTEMPTS = 5;

    public void validateLoginAttempt(String email) {
        LoginAttemptEntity attempt = loginAttemptRepository.findByEmail(email)
                .orElse(LoginAttemptEntity.builder()
                        .email(email)
                        .failCount(0)
                        .isLocked(false)
                        .build());

        if (attempt.isLocked()) {
            throw new LoginException("계정이 잠금 처리되었습니다. 관리자에게 문의하세요.");
        }
    }

    @Transactional
    public void handleLoginSuccess(String email) {
        loginAttemptRepository.findByEmail(email)
                .ifPresent(attempt -> {
                    attempt.resetFailCount();
                    loginAttemptRepository.save(attempt);
                });
    }

    @Transactional
    public void handleLoginFailure(String email) {
        LoginAttemptEntity attempt = loginAttemptRepository.findByEmail(email)
                .orElseGet(() -> {
                    LoginAttemptEntity newAttempt = LoginAttemptEntity.builder()
                            .email(email)
                            .failCount(0)
                            .isLocked(false)
                            .build();
                    return loginAttemptRepository.save(newAttempt);
                });

        if (attempt.isLocked()) {
            throw new LoginException("계정이 잠금 처리되었습니다. 관리자에게 문의하세요.");
        }

        attempt.increaseFailCount();

        if (attempt.getFailCount() >= MAX_ATTEMPTS) {
            attempt.setLocked(true);
            attempt.setLockedTime(LocalDateTime.now());
            loginAttemptRepository.save(attempt);
            throw new LoginException("비밀번호를 5회 이상 잘못 입력하여 계정이 잠겼습니다. 관리자에게 문의하세요.");
        }

        loginAttemptRepository.save(attempt);
    }

    /**
     *TODO : 관리자(Master) 관련 로직 추가.
     */
    @Transactional
    public void unlockAccount(String adminEmail, String targetEmail) {
        MemberEntity admin = memberRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new MemberException("관리자 계정을 찾을 수 없습니다."));

        if (!admin.getTier().equals(TierType.MASTER)) {
            throw new LoginException("계정 잠금 해제는 관리자만 가능합니다.");
        }

        LoginAttemptEntity attempt = loginAttemptRepository.findByEmail(targetEmail)
                .orElseThrow(() -> new LoginException("해당 계정의 로그인 시도 기록을 찾을 수 없습니다."));

        attempt.resetFailCount();

        loginAttemptRepository.save(attempt);
    }
}
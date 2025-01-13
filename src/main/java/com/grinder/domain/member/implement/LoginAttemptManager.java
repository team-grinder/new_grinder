package com.grinder.domain.member.implement;

import com.grinder.common.exception.LoginException;
import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.domain.member.entity.LoginAttemptEntity;
import com.grinder.domain.member.entity.MemberEntity;
import com.grinder.domain.member.model.TierType;
import com.grinder.domain.member.repository.MemberRepository;
import com.grinder.domain.member.repository.login.LoginAttemptRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 1. 로그인 시도기록 조회 -> 없으면 생성,잠기면 예외 발생
 * 2. 로그인 성공 시 실패 카운트 초기화
 * 3. 로그인 실패 시 카운트 증가, 카운트 초과면 잠금처리
 */
@Component
@RequiredArgsConstructor
public class LoginAttemptManager {
    private final LoginAttemptRepository loginAttemptRepository;
    private final MemberRepository memberRepository;
    private static final int MAX_ATTEMPTS = 10;

    public void validateLoginAttempt(String email) {
        LoginAttemptEntity attempt = loginAttemptRepository.findByEmail(email)
                .orElse(LoginAttemptEntity.builder()
                        .email(email)
                        .failCount(0)
                        .isLocked(false)
                        .build());

        if (attempt.isLocked()) {
            throw new LockedException(AuthResultEnum.ACCOUNT_LOCKED.getMessage());
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
    @Transactional(noRollbackFor = LoginException.class)
    public void handleLoginFailure(String email) {
        LoginAttemptEntity attempt = loginAttemptRepository.findByEmail(email)
                .orElseGet(() -> createNewLoginAttempt(email));

        if (attempt.isLocked()) {
            throw new LoginException(AuthResultEnum.ACCOUNT_LOCKED.getMessage());
        }

        attempt.increaseFailCount();

        if (attempt.getFailCount() >= MAX_ATTEMPTS) {
            attempt.setLocked(true);
            attempt.setLockedTime(LocalDateTime.now());
            loginAttemptRepository.save(attempt);
            throw new LoginException(AuthResultEnum.MAX_LOGIN_ATTEMPTS.getMessage());
        }

        loginAttemptRepository.save(attempt);
    }

    private LoginAttemptEntity createNewLoginAttempt(String email) {
        LoginAttemptEntity newAttempt = LoginAttemptEntity.builder()
                .email(email)
                .failCount(0)
                .isLocked(false)
                .build();
        return loginAttemptRepository.save(newAttempt);
    }

    /**
     *TODO : 관리자(Master) 관련 로직 추가.
     */
    @Transactional
    public void unlockAccount(String adminEmail, String targetEmail) {
        MemberEntity admin = memberRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new MemberException(AuthResultEnum.MEMBER_NOT_FOUND));

        if (!admin.getTier().equals(TierType.MASTER)) {
            throw new LoginException(AuthResultEnum.UNAUTHORIZED_ACCESS.getMessage());
        }

        LoginAttemptEntity attempt = loginAttemptRepository.findByEmail(targetEmail)
                .orElseThrow(() -> new LoginException(AuthResultEnum.LOGIN_ATTEMPT_NOT_FOUND.getMessage()));

        attempt.resetFailCount();

        loginAttemptRepository.save(attempt);
    }
}
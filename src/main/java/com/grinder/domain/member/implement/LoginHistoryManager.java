package com.grinder.domain.member.implement;

import com.grinder.common.model.ReslutEnum;
import com.grinder.domain.member.entity.LoginHistoryEntity;
import com.grinder.domain.member.model.LoginType;
import com.grinder.domain.member.model.login.MemberConnectionInfo;
import com.grinder.domain.member.repository.login.LoginHistoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LoginHistoryManager {
    private final LoginHistoryRepository loginHistoryRepository;
    private final MemberInfoCollector memberInfoCollector;
    private final HttpServletRequest request;

    /**
     *1.동시 로그인 체크 및 처리
     *2.새로운 로그인 이력 저장
     */
    @Transactional
    public void saveLoginHistory(Long memberId) {
        String message = validateConcurrentLogin(memberId);
        if (message != null) {
            request.setAttribute("loginMessage", message);
        }
        MemberConnectionInfo connectionInfo = memberInfoCollector.connectionInfo();

        LoginHistoryEntity newHistory = LoginHistoryEntity.builder()
                .memberId(memberId)
                .ipAddress(connectionInfo.getIpAddress())
                .deviceInfo(connectionInfo.getDeviceInfo())
                .lastLoginDate(LocalDateTime.now())
                .isActive(true)
                .loginType(determineLoginType())
                .build();

        loginHistoryRepository.save(newHistory);
    }

    /**
     * 동시로그인 판단
     */
    private String validateConcurrentLogin(Long memberId) {
        List<LoginHistoryEntity> activeLogins = loginHistoryRepository
                .findByMemberIdAndIsActiveOrderByLastLoginDate(memberId, true);

        if (activeLogins.size() >= 3) {
            LoginHistoryEntity oldestLogin = activeLogins.get(0);
            oldestLogin.setActive(false);
            loginHistoryRepository.save(oldestLogin);
            return ReslutEnum.SUCCESS.getMessage() + " (다른 기기에서 접속 중인 세션이 종료되었습니다)";
        }
        return null;
    }
    private LoginType determineLoginType() {
        String requestURI = request.getRequestURI();
        return requestURI.contains("/oauth2/") ? LoginType.SOCIAL : LoginType.COMMON;
    }
}
package com.grinder.domain.member.model.login;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class LoginHistory {
    private Long id;
    private Long memberId;
    private String ipAddress;
    private String deviceInfo;
    private LocalDateTime lastLoginDate;
    private boolean isActive;
}
package com.grinder.domain.member.model.login;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class LoginAttempt {
    private Long id;
    private Long memberId;
    private int failCount;
    private boolean isLocked;
    private LocalDateTime lockedTime;
}

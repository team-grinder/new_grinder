package com.grinder.domain.member.model.login;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberConnectionInfo {
    private String ipAddress;
    private String deviceInfo;
    private LocalDateTime connectionTime;
}

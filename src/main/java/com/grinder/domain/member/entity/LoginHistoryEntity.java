package com.grinder.domain.member.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.member.model.LoginType;
import com.grinder.domain.member.model.login.LoginHistory;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
public class LoginHistoryEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private String ipAddress;
    private String deviceInfo;
    private LocalDateTime lastLoginDate;
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginType loginType;
    public LoginHistory toLoginHistory() {
        return LoginHistory.builder()
                .id(id)
                .memberId(memberId)
                .ipAddress(ipAddress)
                .deviceInfo(deviceInfo)
                .lastLoginDate(lastLoginDate)
                .isActive(isActive)
                .build();
    }


}

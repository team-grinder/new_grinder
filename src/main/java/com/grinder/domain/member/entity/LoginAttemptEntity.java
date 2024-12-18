package com.grinder.domain.member.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.member.model.login.LoginAttempt;
import java.time.LocalDateTime;
import javax.persistence.Entity;
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
public class LoginAttemptEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private Long memberId;

    private int failCount;

    private boolean isLocked;

    private LocalDateTime lockedTime;

    public void increaseFailCount() {
        this.failCount++;
        if (this.failCount >= 5) {
            this.isLocked = true;
            this.lockedTime = LocalDateTime.now();
        }
    }

    public void resetFailCount() {
        this.failCount = 0;
        this.isLocked = false;
        this.lockedTime = null;
    }

    public LoginAttempt toLoginAttempt() {
        return LoginAttempt.builder()
                .id(id)
                .memberId(memberId)
                .failCount(failCount)
                .isLocked(isLocked)
                .lockedTime(lockedTime)
                .build();
    }
}

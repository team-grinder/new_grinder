package com.grinder.domain.member.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.common.security.common.model.MemberType;
import com.grinder.domain.member.model.login.LoginAttempt;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
public class LoginAttemptEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private MemberType memberType;

    private String email;

    private Long memberId;

    private int failCount;

    @Column(columnDefinition = "BOOLEAN")
    private boolean isLocked;

    private LocalDateTime lockedTime;

    public void increaseFailCount() {
        this.failCount = this.failCount + 1;
    }


    public void setLocked(boolean locked) {
        this.isLocked = locked;
        if (locked) {
            this.lockedTime = LocalDateTime.now();
        }
    }

    public void setLocked(boolean locked, LocalDateTime lockedTime) {
        this.setLocked(locked);
        this.lockedTime = lockedTime;
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

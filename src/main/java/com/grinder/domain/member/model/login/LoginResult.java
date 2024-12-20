package com.grinder.domain.member.model.login;

import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.model.TierType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    private Long memberId;
    private String email;
    private TierType tier;
    private String message;

    public LoginResult(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.tier = member.getTier();
    }

    public LoginResult(Member member, String message) {
        this(member);
        this.message = message;
    }
}
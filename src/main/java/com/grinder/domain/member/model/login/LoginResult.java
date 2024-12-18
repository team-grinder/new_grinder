package com.grinder.domain.member.model.login;

import com.grinder.domain.member.model.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResult {
    private Member member;
    private String message;

    public static LoginResult success(Member member, String message) {
        return LoginResult.builder()
                .member(member)
                .message(message)
                .build();
    }
}
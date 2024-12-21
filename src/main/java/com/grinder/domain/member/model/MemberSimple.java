package com.grinder.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberSimple {
    private final Long id;
    private final String nickname;
    private final String imageUrl;
}

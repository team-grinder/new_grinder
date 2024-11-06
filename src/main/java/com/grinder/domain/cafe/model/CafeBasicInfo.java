package com.grinder.domain.cafe.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeBasicInfo {
    private Long id;

    private String name;

    private String address;

    private String description;

    private String tel;
}

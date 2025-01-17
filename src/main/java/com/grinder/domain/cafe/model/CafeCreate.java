package com.grinder.domain.cafe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeCreate {
    private String name;
    private String address;
    private String description;
    private String tel;
    private String businessNumber;
}

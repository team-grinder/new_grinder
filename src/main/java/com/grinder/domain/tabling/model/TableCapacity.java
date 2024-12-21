package com.grinder.domain.tabling.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TableCapacity {
    private Long id;
    private Long cafeId;
    private Integer minCapacity;
    private Integer maxCapacity;
}

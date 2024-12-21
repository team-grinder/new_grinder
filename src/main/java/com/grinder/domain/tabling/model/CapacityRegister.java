package com.grinder.domain.tabling.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CapacityRegister {
    private Integer minCapacity;
    private Integer maxCapacity;
}

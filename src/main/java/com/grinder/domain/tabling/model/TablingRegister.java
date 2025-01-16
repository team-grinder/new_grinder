package com.grinder.domain.tabling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TablingRegister {
    private Long cafeId;
    private Long memberId;
    private LocalDate date;
    private LocalTime reserveTime;
    private Integer numberOfGuests;
}

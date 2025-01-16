package com.grinder.domain.tabling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tabling {
    private Long id;
    private Long cafeId;
    private Long memberId;
    private LocalDate date;
    private LocalTime reserveTime;
    private Integer numberOfGuests;
    private TablingStatus status;
}

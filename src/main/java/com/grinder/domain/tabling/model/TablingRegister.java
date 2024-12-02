package com.grinder.domain.tabling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TablingRegister {
    private Long cafeId;
    private LocalDate reservationDate;
    private LocalTime timeSlot;
    private Integer numberOfPeople;
    private Long memberId;
}

package com.grinder.domain.tabling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Tabling {
    private Long id;
    private Long memberId;
    private Long cafeId;
    private LocalDate reservationDate;
    private LocalTime timeSlot;
    private Integer numberOfPeople;
    private TablingStatus status;
    private Long cartId;
}

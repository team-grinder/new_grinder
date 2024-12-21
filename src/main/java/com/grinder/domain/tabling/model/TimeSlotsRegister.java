package com.grinder.domain.tabling.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TimeSlotsRegister {
    private LocalDate date;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Integer maxReservations;
}

package com.grinder.domain.tabling.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotSetting {
    private LocalDate date;
    private List<TimeSlotsRegister> timeSlots;
}

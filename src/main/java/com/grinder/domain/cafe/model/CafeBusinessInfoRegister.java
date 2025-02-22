package com.grinder.domain.cafe.model;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CafeBusinessInfoRegister {
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxTimePerReservation;
    private Integer maxGuestsPerTime;
    private List<LocalTime> blockedTimes;
}

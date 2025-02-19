package com.grinder.domain.cafe.model;

import java.time.LocalTime;
import java.util.List;
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

    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxTimePerReservation;
    private Integer maxGuestsPerTime;
    private List<LocalTime> blockedTimes;
}

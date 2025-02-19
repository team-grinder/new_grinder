package com.grinder.domain.cafe.model;

import com.grinder.domain.cafe.entity.CafeBusinessHourEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CafeBusinessInfo {
    private int startTime;
    private int endTime;
    private int maxTime;
    private int maxGuestsPerTime;
    private List<Integer> invalidList;

    public static CafeBusinessInfo from(CafeBusinessHourEntity entity) {
        return CafeBusinessInfo.builder()
                .startTime(entity.getStartTime().getHour())
                .endTime(entity.getEndTime().getHour())
                .maxTime(entity.getMaxTimePerReservation())
                .invalidList(entity.getBlockedTimes().stream()
                        .map(LocalTime::getHour)
                        .collect(Collectors.toList()))
                .build();
    }
}

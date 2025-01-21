package com.grinder.domain.tabling.model;

import com.grinder.domain.tabling.entity.TablingEntity;
import com.grinder.domain.tabling.entity.TablingTimeSlotEntity;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AvailableTime {
    private List<TimeSlotInfo> timeSlots;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class TimeSlotInfo {
        private String time;
        private int maxGuests;
        private int availableGuests;
        private boolean isAvailable;
    }

    public static AvailableTime from(List<TablingTimeSlotEntity> timeSlots, List<TablingEntity> existingTablings) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<TimeSlotInfo> timeSlotInfos = timeSlots.stream()
                .map(slot -> {
                    int reservedGuests = calculateReservedGuests(existingTablings, slot.getReserveTime());
                    return TimeSlotInfo.builder()
                            .time(slot.getReserveTime().format(formatter))
                            .maxGuests(slot.getMaxGuests())
                            .availableGuests(slot.getMaxGuests() - reservedGuests)
                            .isAvailable(slot.getIsAvailable() && (slot.getMaxGuests() - reservedGuests > 0))
                            .build();
                })
                .collect(Collectors.toList());

        return AvailableTime.builder()
                .timeSlots(timeSlotInfos)
                .build();
    }

    private static int calculateReservedGuests(List<TablingEntity> tablings, LocalTime time) {
        return tablings.stream()
                .filter(tabling -> tabling.getReserveTime().equals(time))
                .mapToInt(TablingEntity::getNumberOfGuests)
                .sum();
    }
}
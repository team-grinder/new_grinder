package com.grinder.domain.tabling.implement;

import com.grinder.domain.cafe.model.CafeBusinessInfo;
import com.grinder.domain.tabling.model.TimeSlotsRegister;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BusinessHourTemplate {
    protected final TablingTimeSlotManager timeSlotManager;

    public List<TimeSlotsRegister> createTimeSlots(Long cafeId, CafeBusinessInfo businessHour) {
        List<TimeSlotsRegister> timeSlots = new ArrayList<>();
        LocalTime currentTime = LocalTime.of(businessHour.getStartTime(), 0);
        LocalTime endTime = LocalTime.of(businessHour.getEndTime(), 0);

        while (!currentTime.isAfter(endTime)) {
            if (isValidTimeSlot(businessHour, currentTime)) {
                timeSlots.add(TimeSlotsRegister.builder()
                        .reserveTime(currentTime)
                        .maxGuests(calculateMaxGuests(cafeId, businessHour))
                        .build());
            }
            currentTime = currentTime.plusMinutes(60);
        }
        return timeSlots;
    }

    protected boolean isValidTimeSlot(CafeBusinessInfo businessHour, LocalTime time) {
        return !businessHour.getInvalidList().contains(time.getHour());
    }

    protected abstract int calculateMaxGuests(Long cafeId, CafeBusinessInfo businessHour);
}

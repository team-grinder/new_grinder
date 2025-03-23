package com.grinder.domain.tabling.implement;

import com.grinder.domain.cafe.model.CafeBusinessInfo;
import com.grinder.domain.tabling.model.TimeSlotsRegister;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class BusinessHourTemplate {

    public List<TimeSlotsRegister> createTimeSlots(Long cafeId, CafeBusinessInfo businessHour) {
        List<TimeSlotsRegister> timeSlots = new ArrayList<>();
        LocalTime currentTime = LocalTime.of(businessHour.getStartTime(), 0);
        LocalTime endTime = LocalTime.of(businessHour.getEndTime(), 0);

        boolean isOvernight = endTime.isBefore(currentTime);

        while (true) {
            if (!businessHour.getInvalidList().contains(currentTime.getHour())) {
                timeSlots.add(TimeSlotsRegister.builder()
                        .reserveTime(currentTime)
                        .maxGuests(calculateMaxGuests(cafeId, businessHour))
                        .build());
            }
            currentTime = currentTime.plusMinutes(60);

            if (!isOvernight && !currentTime.isBefore(endTime)) {
                break;
            } else if (isOvernight && currentTime.equals(LocalTime.MIDNIGHT)) {
                break;
            }
        }

        log.info("타임슬롯 생성 완료 - 생성된 슬롯 수: {}", timeSlots.size());
        return timeSlots;
    }

    protected boolean isValidTimeSlot(CafeBusinessInfo businessHour, LocalTime time) {
        return !businessHour.getInvalidList().contains(time.getHour());
    }

    protected abstract int calculateMaxGuests(Long cafeId, CafeBusinessInfo businessHour);
}

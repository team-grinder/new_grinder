package com.grinder.domain.tabling.service;

import com.grinder.common.exception.TablingException;
import com.grinder.domain.tabling.implement.TablingTimeSlotManager;
import com.grinder.domain.tabling.model.TablingTimeSlot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TablingTimeSlotService {
    private final TablingTimeSlotManager timeSlotManager;

    @Transactional
    public void createTimeSlots(Long cafeId, LocalDate date,
                                LocalTime openTime, LocalTime closeTime, int maxReservations) {
        validateDate(date);
        validateTime(openTime, closeTime);
        timeSlotManager.createBusinessHourTimeSlots(cafeId, date, openTime, closeTime, maxReservations);
    }

    public List<TablingTimeSlot> getAvailableTimeSlots(Long cafeId, LocalDate date) {
        validateDate(date);
        return timeSlotManager.getAvailableTimeSlots(cafeId, date);
    }

    private void validateDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new TablingException("과거 날짜는 처리할 수 없습니다.");
        }
    }

    private void validateTime(LocalTime openTime, LocalTime closeTime) {
        if (openTime.isAfter(closeTime)) {
            throw new TablingException("종료 시간은 시작 시간보다 늦어야 합니다.");
        }
        if (openTime.equals(closeTime)) {
            throw new TablingException("영업 시작 시간과 종료 시간이 같을 수 없습니다.");
        }
    }

}

package com.grinder.domain.tabling.service;

import com.grinder.domain.cafe.model.CafeBusinessInfo;
import com.grinder.domain.tabling.implement.TablingTimeSlotManager;
import com.grinder.domain.tabling.implement.WeekdayBusinessHour;
import com.grinder.domain.tabling.implement.WeekendBusinessHour;
import com.grinder.domain.tabling.model.TimeSlotsRegister;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TablingTimeSlotService {
    private final TablingTimeSlotManager timeSlotManager;
    private final WeekdayBusinessHour weekdayTemplate;
    private final WeekendBusinessHour weekendTemplate;

    @Transactional
    public void setTimeSlots(Long cafeId, LocalDate date, List<TimeSlotsRegister> timeSlots) {
        timeSlotManager.setTimeSlots(cafeId, date, timeSlots);
    }

    public void updateTimeSlots(Long cafeId, LocalDate date, List<TimeSlotsRegister> timeSlots) {
        timeSlotManager.updateTimeSlots(cafeId, date, timeSlots);
    }

    @Transactional
    public void generateTimeSlots(Long cafeId, LocalDate date, CafeBusinessInfo businessHour) {
        if (timeSlotManager.existsTimeSlots(cafeId, date)) {
            return;
        }

        List<TimeSlotsRegister> timeSlots;
        if (isWeekend(date)) {
            timeSlots = weekendTemplate.createTimeSlots(cafeId, businessHour);
        } else {
            timeSlots = weekdayTemplate.createTimeSlots(cafeId, businessHour);
        }

        timeSlotManager.setTimeSlots(cafeId, date, timeSlots);
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}


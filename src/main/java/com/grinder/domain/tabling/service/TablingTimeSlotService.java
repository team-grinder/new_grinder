package com.grinder.domain.tabling.service;

import com.grinder.domain.tabling.implement.TablingTimeSlotManager;
import com.grinder.domain.tabling.model.TimeSlotsRegister;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TablingTimeSlotService {
    private final TablingTimeSlotManager tablingTimeSlotManager;

    @Transactional
    public void setTimeSlots(Long cafeId, LocalDate date, List<TimeSlotsRegister> timeSlots) {
        tablingTimeSlotManager.setTimeSlots(cafeId, date, timeSlots);
    }
}

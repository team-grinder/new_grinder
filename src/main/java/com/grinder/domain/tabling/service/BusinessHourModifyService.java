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
public class BusinessHourModifyService {
    private final TablingTimeSlotManager timeSlotManager;

    public void modifyTimeSlots(Long cafeId, LocalDate date, List<TimeSlotsRegister> timeSlots) {

        handleExistingReservations(cafeId, date, timeSlots);

        timeSlotManager.updateTimeSlots(cafeId, date, timeSlots);
    }

    private void handleExistingReservations(Long cafeId, LocalDate date,
                                            List<TimeSlotsRegister> newTimeSlots) {
    }
}
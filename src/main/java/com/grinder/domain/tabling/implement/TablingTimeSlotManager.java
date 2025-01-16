package com.grinder.domain.tabling.implement;

import com.grinder.domain.tabling.entity.TablingTimeSlotEntity;
import com.grinder.domain.tabling.model.TimeSlotsRegister;
import com.grinder.domain.tabling.repository.TablingTimeSlotRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TablingTimeSlotManager {
    private final TablingTimeSlotRepository tablingTimeSlotRepository;

    public void setTimeSlots(Long cafeId, LocalDate date, List<TimeSlotsRegister> timeSlots) {
        tablingTimeSlotRepository.deleteByCafeIdAndDate(cafeId, date);

        List<TablingTimeSlotEntity> timeSlotEntities = timeSlots.stream()
                .map(slot -> TablingTimeSlotEntity.builder()
                        .cafeId(cafeId)
                        .date(date)
                        .reserveTime(slot.getReserveTime())
                        .maxGuests(slot.getMaxGuests())
                        .isAvailable(true)
                        .build())
                .collect(Collectors.toList());

        tablingTimeSlotRepository.saveAll(timeSlotEntities);
    }

    public Optional<TablingTimeSlotEntity> getTimeSlot(
            Long cafeId, LocalDate date, LocalTime hourTime) {
        return tablingTimeSlotRepository
                .findByCafeIdAndDateAndReserveTime(cafeId, date, hourTime);
    }

    public Optional<TablingTimeSlotEntity> getTimeSlotWithLock(
            Long cafeId, LocalDate date, LocalTime hourTime) {
        return tablingTimeSlotRepository.findWithLockByCafeIdAndDateAndReserveTime(cafeId, date, hourTime);
    }
}


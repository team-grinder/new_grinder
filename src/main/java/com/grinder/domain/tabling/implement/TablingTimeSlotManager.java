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
                        .currentGuests(0)
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

    public List<TablingTimeSlotEntity> getTimeSlots(Long cafeId, LocalDate date) {
        return tablingTimeSlotRepository.findByCafeIdAndDate(cafeId, date);
    }

    public Optional<TablingTimeSlotEntity> getTimeSlotWithLock(
            Long cafeId, LocalDate date, LocalTime hourTime) {
        return tablingTimeSlotRepository.findWithLockByCafeIdAndDateAndReserveTime(cafeId, date, hourTime);
    }

    public void updateTimeSlots(Long cafeId, LocalDate date, List<TimeSlotsRegister> timeSlots) {
        List<TablingTimeSlotEntity> existingSlots =
                tablingTimeSlotRepository.findByCafeIdAndDate(cafeId, date);

        List<TablingTimeSlotEntity> updatedSlots = timeSlots.stream()
                .map(slot -> {
                    TablingTimeSlotEntity existing = existingSlots.stream()
                            .filter(e -> e.getReserveTime().equals(slot.getReserveTime()))
                            .findFirst()
                            .orElse(null);

                    if (existing != null) {
                        existing.setMaxGuests(slot.getMaxGuests());
                        return existing;
                    } else {
                        return TablingTimeSlotEntity.builder()
                                .cafeId(cafeId)
                                .date(date)
                                .reserveTime(slot.getReserveTime())
                                .maxGuests(slot.getMaxGuests())
                                .currentGuests(0)
                                .isAvailable(true)
                                .build();
                    }
                })
                .collect(Collectors.toList());

        tablingTimeSlotRepository.saveAll(updatedSlots);
    }

    public boolean existsTimeSlots(Long cafeId, LocalDate date) {
        return !tablingTimeSlotRepository.findByCafeIdAndDate(cafeId, date).isEmpty();
    }
}


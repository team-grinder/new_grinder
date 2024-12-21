package com.grinder.domain.tabling.implement;

import com.grinder.common.exception.TablingException;
import com.grinder.domain.tabling.entity.TablingTimeSlotEntity;
import com.grinder.domain.tabling.model.TablingTimeSlot;
import com.grinder.domain.tabling.repository.TablingTimeSlotRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TablingTimeSlotManager {
    private final TablingTimeSlotRepository tablingTimeSlotRepository;


    public void createTimeSlot(Long cafeId, LocalDate date, LocalTime time, int maxReservations) {
        TablingTimeSlotEntity timeSlot = TablingTimeSlotEntity.builder()
                .cafeId(cafeId)
                .reservationDate(date)
                .timeSlot(time)
                .maxReservations(maxReservations)
                .currentReservations(0)
                .build();

        tablingTimeSlotRepository.save(timeSlot);
    }

    public void createBusinessHourTimeSlots(Long cafeId, LocalDate date,
                                            LocalTime openTime, LocalTime closeTime, int maxReservations) {
        LocalTime lastBookableTime = closeTime.minusHours(1);

        LocalTime currentSlot = openTime;
        while (currentSlot.isBefore(lastBookableTime) || currentSlot.equals(lastBookableTime)) {
            createTimeSlot(cafeId, date, currentSlot, maxReservations);
            currentSlot = currentSlot.plusHours(1);
        }
    }

    public List<TablingTimeSlot> getAvailableTimeSlots(Long cafeId, LocalDate date) {
        return tablingTimeSlotRepository.findAvailableTimeSlots(cafeId, date)
                .stream()
                .map(entity -> TablingTimeSlot.builder()
                        .id(entity.getId())
                        .cafeId(entity.getCafeId())
                        .reservationDate(entity.getReservationDate())
                        .timeSlot(entity.getTimeSlot())
                        .maxReservations(entity.getMaxReservations())
                        .currentReservations(entity.getCurrentReservations())
                        .available(entity.getCurrentReservations() < entity.getMaxReservations())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void validateTimeSlotAndIncrement(Long cafeId, LocalDate date, LocalTime time){
        TablingTimeSlotEntity timeSlot = tablingTimeSlotRepository.findForUpdate(cafeId,date,time)
                .orElseThrow(()-> new TablingException("예약시간을 확인할 수 없습니다."));
        if(!timeSlot.isAvailable()){
            throw new TablingException("예약 시간이 마감되었습니다.");
        }

        timeSlot.incrementTabling();
    }

}

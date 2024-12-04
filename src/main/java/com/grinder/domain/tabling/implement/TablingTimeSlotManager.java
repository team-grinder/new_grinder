package com.grinder.domain.tabling.implement;

import com.grinder.common.exception.TablingException;
import com.grinder.domain.tabling.entity.TablingTimeSlotEntity;
import com.grinder.domain.tabling.repository.TablingTimeSlotRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TablingTimeSlotManager {
    private final TablingTimeSlotRepository tablingTimeSlotRepository;

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

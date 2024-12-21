package com.grinder.domain.tabling.implement;

import com.grinder.domain.tabling.entity.TablingEntity;
import com.grinder.domain.tabling.model.TableCapacity;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.model.TablingStatus;
import com.grinder.domain.tabling.repository.TablingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TablingManager {
    private final TablingRepository tablingRepository;
    private final TablingCapacityManager tablingCapacityManager;
    private final TablingTimeSlotManager tablingTimeSlotManager;

    /**
     * 1.수용인원체크
     * 2.시간대 예약 가능 체크
     * 3.테이블링 생성
     */
    @Transactional
    public Tabling createTabling(TablingRegister register){
        TableCapacity capacity = tablingCapacityManager.readTableCapacity(register.getCafeId());
        tablingCapacityManager.validateCapacity(capacity,register.getNumberOfPeople());

        tablingTimeSlotManager.validateTimeSlotAndIncrement(register.getCafeId(),register.getTablingDate(),register.getTimeSlot());

        TablingEntity tablingEntity = TablingEntity.builder()
                .memberId(register.getMemberId())
                .cafeId(register.getCafeId())
                .tablingDate(register.getTablingDate())
                .timeSlot(register.getTimeSlot())
                .currentNumber(register.getNumberOfPeople())
                .status(TablingStatus.PENDING)
                .build();

        return tablingRepository.save(tablingEntity).toTabling();
    }
}

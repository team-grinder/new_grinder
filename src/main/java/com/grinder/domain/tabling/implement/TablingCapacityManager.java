package com.grinder.domain.tabling.implement;

import com.grinder.common.exception.TablingException;
import com.grinder.domain.tabling.model.TableCapacity;
import com.grinder.domain.tabling.repository.TablingCapacityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TablingCapacityManager {
    private final TablingCapacityRepository tablingCapacityRepository;

    public TableCapacity readTableCapacity(Long cafeId) {
        return tablingCapacityRepository.findByCafeId(cafeId)
                .map(entity -> TableCapacity.builder()
                        .id(entity.getId())
                        .cafeId(entity.getCafeId())
                        .minCapacity(entity.getMinCapacity())
                        .maxCapacity(entity.getMaxCapacity())
                        .build())
                .orElseThrow(() -> new TablingException("존재하지 않는 카페 아이디 입니다."));
    }

    public void validateCapacity(TableCapacity capacity, int numOfPeople) {
        if (numOfPeople < capacity.getMinCapacity() ||
                numOfPeople > capacity.getMaxCapacity()) {
            throw new TablingException("예약할 수 없는 인원 수 입니다.");
        }
    }


}

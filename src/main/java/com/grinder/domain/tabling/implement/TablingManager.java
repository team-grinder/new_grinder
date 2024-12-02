package com.grinder.domain.tabling.implement;

import com.grinder.common.exception.TablingException;
import com.grinder.domain.tabling.model.TableCapacity;
import com.grinder.domain.tabling.repository.TableCapacityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TablingManager {
    private final TableCapacityRepository tableCapacityRepository;
    public TableCapacity readTableCapacity(Long cafeId) {
        return tableCapacityRepository.findByCafeId(cafeId)
                .map(entity -> TableCapacity.builder()
                        .id(entity.getId())
                        .cafeId(entity.getCafeId())
                        .minCapacity(entity.getMinCapacity())
                        .maxCapacity(entity.getMaxCapacity())
                        .build())
                .orElseThrow(() -> new TablingException("존재하지 않는 카페 아이디 입니다."));
    }
}

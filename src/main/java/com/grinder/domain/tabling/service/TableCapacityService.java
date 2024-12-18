package com.grinder.domain.tabling.service;

import com.grinder.domain.tabling.implement.TablingCapacityManager;
import com.grinder.domain.tabling.model.TableCapacity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableCapacityService {
    private final TablingCapacityManager tableCapacityManager;

    public TableCapacity createCapacity(Long cafeId, int minCapacity, int maxCapacity) {
        validateCapacityValues(minCapacity, maxCapacity);
        return tableCapacityManager.save(cafeId, minCapacity, maxCapacity);
    }

    public TableCapacity getCapacity(Long cafeId) {
        return tableCapacityManager.readTableCapacity(cafeId);
    }

    private void validateCapacityValues(int minCapacity, int maxCapacity) {
        if (minCapacity <= 0 || maxCapacity <= 0) {
            throw new IllegalArgumentException("수용 인원은 0보다 커야 합니다.");
        }
        if (minCapacity > maxCapacity) {
            throw new IllegalArgumentException("최소 수용 인원이 최대 수용 인원보다 클 수 없습니다.");
        }
    }
}

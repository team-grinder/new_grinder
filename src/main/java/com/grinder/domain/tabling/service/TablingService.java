package com.grinder.domain.tabling.service;

import com.grinder.domain.tabling.implement.TablingManager;
import com.grinder.domain.tabling.model.TableCapacity;
import com.grinder.domain.tabling.repository.TablingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TablingService {
    private final TablingManager tablingManager;

    @Transactional(readOnly = true)
    public TableCapacity getTableCapacity(Long cafeId){
        return tablingManager.readTableCapacity(cafeId);
    }
}

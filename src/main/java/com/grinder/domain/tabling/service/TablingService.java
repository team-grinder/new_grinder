package com.grinder.domain.tabling.service;

import com.grinder.domain.tabling.implement.TablingCapacityManager;
import com.grinder.domain.tabling.implement.TablingManager;
import com.grinder.domain.tabling.model.TableCapacity;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TablingService {
    private final TablingManager tablingManager;
    private final TablingCapacityManager tablingCapacityManager;

    @Transactional(readOnly = true)
    public TableCapacity getTableCapacity(Long cafeId){
        return tablingCapacityManager.readTableCapacity(cafeId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Tabling tryTabling(TablingRegister register){
        return tablingManager.createTabling(register);
    }
}

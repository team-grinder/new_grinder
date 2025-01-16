package com.grinder.domain.tabling.service;

import com.grinder.domain.tabling.implement.TablingManager;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.model.TablingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TablingService {
    private final TablingManager tablingManager;

    @Transactional
    public Tabling createTabling(TablingRegister request) {
        return tablingManager.createTabling(request);
    }
    @Transactional
    public void updateTablingStatus(Long tablingId, TablingStatus status) {
        tablingManager.updateTablingStatus(tablingId, status);
    }
}

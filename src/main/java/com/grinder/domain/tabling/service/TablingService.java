package com.grinder.domain.tabling.service;

import com.grinder.domain.tabling.implement.TablingManager;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.model.TablingStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    @Transactional
    public void cancelTabling(Long tablingId, Long memberId) {
        tablingManager.cancelTabling(tablingId, memberId);
    }
    public List<Tabling> getMemberTablings(Long memberId, List<TablingStatus> statuses) {
        return tablingManager.getMemberTablings(memberId, statuses);
    }

    public List<Tabling> getCafeTablings(Long cafeId, LocalDate date) {
        return tablingManager.getCafeTablings(cafeId, date);
    }

    public List<Tabling> getCafeTablingsBetween(Long cafeId, LocalDate startDate, LocalDate endDate) {
        return tablingManager.getCafeTablingsBetween(cafeId, startDate, endDate);
    }

    public List<Tabling> getCafeTablingsByStatus(Long cafeId, LocalDate date, List<TablingStatus> statuses) {
        return tablingManager.getCafeTablingsByStatus(cafeId, date, statuses);
    }

    public List<Tabling> getTimeSlotTablings(
            LocalDate date,
            LocalTime reserveTime,
            List<TablingStatus> statuses) {
        return tablingManager.getTimeSlotTablings(date, reserveTime, statuses);
    }
}

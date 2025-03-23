package com.grinder.domain.cafe.service;

import com.grinder.common.model.Pages;
import com.grinder.domain.cafe.implement.CafeBusinessHourManager;
import com.grinder.domain.cafe.implement.CafeManager;
import com.grinder.domain.cafe.implement.CafeReader;
import com.grinder.domain.cafe.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {
    private final CafeReader cafeReader;
    private final CafeManager cafeManager;
    private final CafeBusinessHourManager cafeBusinessHourManager;

    public List<Cafe> getPopularCafe() {
        return cafeManager.findPopularCafe();
    }

    public Pages<Cafe> getCafes(CafeSearchPage searchPage) {
        return cafeReader.findCafePage(searchPage);
    }

    public Cafe getCafe(Long cafeId) {
        return cafeReader.read(cafeId);
    }

    public CafeAndMenu getCafeAndMenu(Long cafeId) {
        return cafeManager.getCafeAndMenu(cafeId);
    }

    public List<Cafe> findCafeByName(String name) {
        return cafeReader.readByName(name);
    }
    @Transactional
    public Cafe createCafe(CafeCreate request) {
        return cafeReader.createCafe(request);
    }

    public void createBusinessHour(Long cafeId, CafeBusinessInfoRegister hourRegister) {
        cafeBusinessHourManager.setOperatingHours(cafeId, hourRegister);
    }

    public Cafe createCafeAndBusinessHour(CafeCreate request) {
        Cafe cafe = this.createCafe(request);

        // 2. 영업시간 정보 저장
        cafeBusinessHourManager.setOperatingHours(
                cafe.getId(),
                CafeBusinessInfoRegister.builder()
                        .startTime(request.getStartTime())
                        .endTime(request.getEndTime())
                        .maxTimePerReservation(request.getMaxTimePerReservation())
                        .maxGuestsPerTime(request.getMaxGuestsPerTime())
                        .blockedTimes(request.getBlockedTimes())
                        .build()
        );

        return cafe;
    }

    @Transactional
    public CafeBusinessInfo setBusinessHours(Long cafeId, CafeBusinessInfoRegister request) {
        return cafeBusinessHourManager.setOperatingHours(cafeId, request);
    }

    public CafeBusinessInfo getBusinessHours(Long cafeId) {
        return cafeBusinessHourManager.getOperatingHours(cafeId);
    }

    @Transactional
    public CafeBusinessInfo updateBusinessHours(Long cafeId, CafeBusinessInfoRegister request) {
        return cafeBusinessHourManager.updateBusinessHours(cafeId, request);
    }

    @Transactional
    public void deleteCafe(Long cafeId) {
        cafeReader.deleteCafe(cafeId);
    }

    @Transactional
    public void updateCafe(Long cafeId, Cafe cafe) {
        cafeReader.updateCafe(cafeId, cafe);
    }
}

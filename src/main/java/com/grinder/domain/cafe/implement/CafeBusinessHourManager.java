package com.grinder.domain.cafe.implement;

import com.grinder.domain.cafe.entity.CafeBusinessHourEntity;
import com.grinder.domain.cafe.model.CafeBusinessInfo;
import com.grinder.domain.cafe.model.CafeBusinessInfoRegister;
import com.grinder.domain.cafe.repository.CafeBusinessHourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CafeBusinessHourManager {
    private final CafeBusinessHourRepository cafeBusinessHourRepository;

    public CafeBusinessInfo getOperatingHours(Long cafeId) {
        return CafeBusinessInfo.from(cafeBusinessHourRepository.findByCafeId(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("영업시간 설정이 없습니다.")));
    }


    public CafeBusinessInfo setOperatingHours(Long cafeId, CafeBusinessInfoRegister request) {
        CafeBusinessHourEntity entity = cafeBusinessHourRepository.findByCafeId(cafeId)
                .orElse(CafeBusinessHourEntity.builder()
                        .cafeId(cafeId)
                        .build());

        entity.updateBusinessHours(
                request.getStartTime(),
                request.getEndTime(),
                request.getMaxTimePerReservation(),
                request.getBlockedTimes()
        );

        return CafeBusinessInfo.from(cafeBusinessHourRepository.save(entity));
    }
}

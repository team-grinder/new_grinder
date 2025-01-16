package com.grinder.domain.tabling.implement;

import com.grinder.common.exception.TablingException;
import com.grinder.domain.tabling.entity.TablingEntity;
import com.grinder.domain.tabling.entity.TablingTimeSlotEntity;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.model.TablingStatus;
import com.grinder.domain.tabling.repository.BlockedDateRepository;
import com.grinder.domain.tabling.repository.TablingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TablingManager {
    private final TablingRepository tablingRepository;
    private final TablingTimeSlotManager tablingTimeSlotManager;
    private final BlockedDateRepository blockedDateRepository;

    @Transactional
    public Tabling createTabling(TablingRegister request) {
        // 1. 휴무일 체크
        if (blockedDateRepository.existsByCafeIdAndDate(request.getCafeId(), request.getDate())) {
            throw new TablingException("해당 날짜는 휴무일입니다.");
        }

        // 2. 먼저 락 없이 예약 가능 여부 체크
        TablingTimeSlotEntity timeSlot = tablingTimeSlotManager
                .getTimeSlot(request.getCafeId(), request.getDate(),
                        request.getReserveTime())
                .orElseThrow(() -> new TablingException("예약 가능한 시간대가 아닙니다."));

        if (!timeSlot.canReserve(request.getNumberOfGuests())) {
            throw new TablingException(
                    String.format("예약 가능한 인원을 초과했습니다. (현재 가능 인원: %d)",
                            timeSlot.getMaxGuests() - timeSlot.getCurrentGuests())
            );
        }

        // 3. 예약 생성 시도 (with 낙관적 락)
        int maxRetries = 3;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                timeSlot = tablingTimeSlotManager
                        .getTimeSlotWithLock(request.getCafeId(), request.getDate(),
                                request.getReserveTime())
                        .orElseThrow(() -> new TablingException("예약 가능한 시간대가 아닙니다."));

                // 한번 더 체크 (동시성 처리)
                if (!timeSlot.canReserve(request.getNumberOfGuests())) {
                    throw new TablingException("예약 가능한 인원을 초과했습니다.");
                }

                timeSlot.addGuests(request.getNumberOfGuests());

                TablingEntity entity = TablingEntity.builder()
                        .cafeId(request.getCafeId())
                        .memberId(request.getMemberId())
                        .date(request.getDate())
                        .reserveTime(request.getReserveTime())
                        .numberOfGuests(request.getNumberOfGuests())
                        .build();

                return tablingRepository.save(entity).toTabling();

            } catch (OptimisticLockingFailureException e) {
                attempt++;
                if (attempt >= maxRetries) {
                    throw new TablingException("예약 처리 중 충돌이 발생했습니다. 다시 시도해주세요.");
                }
                try {
                    Thread.sleep(100 * (attempt + 1));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new TablingException("예약 처리가 중단되었습니다.");
                }
            }
        }
        throw new TablingException("예약 처리에 실패했습니다.");
    }

    @Transactional
    public void updateTablingStatus(Long tablingId, TablingStatus newStatus) {
        TablingEntity tabling = tablingRepository.findById(tablingId)
                .orElseThrow(() -> new TablingException("예약을 찾을 수 없습니다."));

        if (tabling.getStatus() == TablingStatus.CANCEL) {
            throw new TablingException("취소된 예약은 상태를 변경할 수 없습니다.");
        }

        TablingTimeSlotEntity timeSlot = tablingTimeSlotManager
                .getTimeSlotWithLock(tabling.getCafeId(), tabling.getDate(),
                        tabling.getReserveTime())
                .orElseThrow(() -> new TablingException("시간대 정보를 찾을 수 없습니다."));

        // 상태 변경에 따른 처리
        if (newStatus == TablingStatus.CANCEL) {
            timeSlot.removeGuests(tabling.getNumberOfGuests());
        }

        tabling.updateStatus(newStatus);
    }
}
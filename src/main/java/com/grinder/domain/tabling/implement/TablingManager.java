package com.grinder.domain.tabling.implement;

import com.grinder.common.exception.TablingException;
import com.grinder.domain.cafe.implement.CafeBusinessHourManager;
import com.grinder.domain.cafe.model.CafeBusinessInfo;
import com.grinder.domain.tabling.entity.TablingEntity;
import com.grinder.domain.tabling.entity.TablingTimeSlotEntity;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.model.TablingStatus;
import com.grinder.domain.tabling.model.AvailableTime;
import com.grinder.domain.tabling.repository.TablingRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TablingManager {
    private final TablingRepository tablingRepository;
    private final TablingTimeSlotManager tablingTimeSlotManager;
    private final CafeBusinessHourManager cafeBusinessHourManager;
    @Transactional
    public Tabling createTabling(TablingRegister request) {
        CafeBusinessInfo businessHour = cafeBusinessHourManager.getOperatingHours(request.getCafeId());
        // 1. 영업시간 체크
        validateBusinessHours(businessHour, request.getDate(), request.getReserveTime());

        // 2. 동일 회원의 중복 예약 체크
        if (tablingRepository.existsByMemberIdAndDateAndReserveTimeAndStatusNot(
                request.getMemberId(),
                request.getDate(),
                request.getReserveTime(),
                TablingStatus.CANCEL)) {
            throw new TablingException("해당 시간대에 이미 예약이 존재합니다.");
        }

        // 3. 락 없이 예약 가능 여부 체크
        TablingTimeSlotEntity timeSlot = tablingTimeSlotManager
                .getTimeSlot(request.getCafeId(), request.getDate(), request.getReserveTime())
                .orElseThrow(() -> new TablingException("예약 가능한 시간대가 아닙니다."));

        if (!timeSlot.canReserve(request.getNumberOfGuests())) {
            throw new TablingException(
                    String.format("예약 가능한 인원을 초과했습니다. (현재 가능 인원: %d)",
                            timeSlot.getMaxGuests() - timeSlot.getCurrentGuests())
            );
        }

        // 4. 낙관적 락을 통한 예약 처리
        return processTablingWithLock(request, timeSlot);
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

    private Tabling processTablingWithLock(TablingRegister request, TablingTimeSlotEntity timeSlot) {
        int maxRetries = 3;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                timeSlot = tablingTimeSlotManager
                        .getTimeSlotWithLock(request.getCafeId(), request.getDate(), request.getReserveTime())
                        .orElseThrow(() -> new TablingException("예약 가능한 시간대가 아닙니다."));

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
                        .status(TablingStatus.PENDING)
                        .build();

                return tablingRepository.save(entity).toTabling();

            } catch (OptimisticLockingFailureException e) {
                // 재시도 로직
                attempt++;
                if (attempt >= maxRetries) {
                    throw new TablingException("예약 처리 중 충돌이 발생했습니다. 다시 시도해주세요.");
                }
                try {
                    Thread.sleep(100L * (attempt + 1));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new TablingException("예약 처리가 중단되었습니다.");
                }
            }
        }
        throw new TablingException("예약 처리에 실패했습니다.");
    }

    @Transactional
    public void cancelTabling(Long tablingId, Long memberId) {
        TablingEntity tabling = tablingRepository.findById(tablingId)
                .orElseThrow(() -> new TablingException("예약을 찾을 수 없습니다."));

        if (!tabling.getMemberId().equals(memberId)) {
            throw new TablingException("예약 취소 권한이 없습니다.");
        }

        if (tabling.getStatus() == TablingStatus.CANCEL) {
            throw new TablingException("이미 취소된 예약입니다.");
        }

        TablingTimeSlotEntity timeSlot = tablingTimeSlotManager
                .getTimeSlotWithLock(tabling.getCafeId(), tabling.getDate(), tabling.getReserveTime())
                .orElseThrow(() -> new TablingException("시간대 정보를 찾을 수 없습니다."));

        timeSlot.removeGuests(tabling.getNumberOfGuests());
        tabling.updateStatus(TablingStatus.CANCEL);
    }

    // 회원의 예약 내역 조회
    public List<Tabling> getMemberTablings(Long memberId) {
        return tablingRepository.findByMemberId(memberId)
                .stream()
                .map(TablingEntity::toTabling)
                .collect(Collectors.toList());
    }

    public AvailableTime getAvailableTime(Long cafeId, LocalDate date) {
        List<TablingTimeSlotEntity> timeSlots =
                tablingTimeSlotManager.getTimeSlots(cafeId, date);

        List<TablingEntity> existingTablings =
                tablingRepository.findByCafeIdAndDateAndStatusIn(
                        cafeId,
                        date,
                        List.of(TablingStatus.PENDING, TablingStatus.CONFIRMED)
                );

        return AvailableTime.from(timeSlots, existingTablings);
    }

    private void validateBusinessHours(CafeBusinessInfo businessHour, LocalDate date, LocalTime time) {
        // 영업시간 체크
        if (time.getHour() < businessHour.getStartTime() ||
                time.getHour() > businessHour.getEndTime()) {
            throw new TablingException("영업시간이 아닙니다.");
        }

        // 차단된 시간대 체크
        if (businessHour.getInvalidList().contains(time.getHour())) {
            throw new TablingException("예약이 불가능한 시간대입니다.");
        }
    }
}
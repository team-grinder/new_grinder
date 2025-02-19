package com.grinder.domain.tabling.implement;

import com.grinder.domain.cafe.implement.CafeBusinessHourManager;
import com.grinder.domain.cafe.model.CafeBusinessInfo;
import com.grinder.domain.tabling.service.TablingTimeSlotService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class BusinessHourScheduler implements ApplicationRunner {
    private final CafeBusinessHourManager cafeBusinessHourManager;
    private final TablingTimeSlotService timeSlotService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("시작: 초기 타임슬롯 생성");
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(7);
        List<Long> activeCafeIds = cafeBusinessHourManager.getActiveCafeIds();

        for (Long cafeId : activeCafeIds) {
            LocalDate currentDate = today;
            try {
                CafeBusinessInfo businessHour = cafeBusinessHourManager.getOperatingHours(cafeId);
                while (!currentDate.isAfter(endDate)) {
                    timeSlotService.generateTimeSlots(cafeId, currentDate, businessHour);
                    currentDate = currentDate.plusDays(1);
                }
            } catch (Exception e) {
                log.error("타임슬롯 생성 실패: 카페ID {}, 기간: {} ~ {}", cafeId, today, endDate, e);
            }
        }
        log.info("완료: 초기 타임슬롯 생성");
    }

    @Scheduled(cron = "0 0 0 * * SUN")
    @Transactional
    public void createNextWeekTimeSlots() {
        log.info("시작: 다음주 타임슬롯 생성");
        LocalDate nextWeekStart = LocalDate.now().plusDays(7);
        LocalDate endDate = nextWeekStart.plusDays(6);
        List<Long> activeCafeIds = cafeBusinessHourManager.getActiveCafeIds();

        for (Long cafeId : activeCafeIds) {
            LocalDate currentDate = nextWeekStart;
            try {
                CafeBusinessInfo businessHour = cafeBusinessHourManager.getOperatingHours(cafeId);
                while (!currentDate.isAfter(endDate)) {
                    timeSlotService.generateTimeSlots(cafeId, currentDate, businessHour);
                    currentDate = currentDate.plusDays(1);
                }
            } catch (Exception e) {
                log.error("다음주 타임슬롯 생성 실패: 카페ID {}, 기간: {} ~ {}",
                        cafeId, nextWeekStart, endDate, e);
            }
        }
        log.info("완료: 다음주 타임슬롯 생성");
    }
}
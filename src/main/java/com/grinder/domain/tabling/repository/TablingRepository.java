package com.grinder.domain.tabling.repository;

import com.grinder.domain.tabling.entity.TablingEntity;
import com.grinder.domain.tabling.model.TablingStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TablingRepository extends JpaRepository<TablingEntity, Long> {
    List<TablingEntity> findByCafeIdAndDate(Long cafeId, LocalDate date);

    List<TablingEntity> findByCafeIdAndDateBetween(Long cafeId, LocalDate startDate, LocalDate endDate);

    List<TablingEntity> findByCafeIdAndDateAndStatusIn(Long cafeId, LocalDate date, List<TablingStatus> statuses);

    boolean existsByMemberIdAndDateAndReserveTimeAndStatusNot(Long MemberId,LocalDate date, LocalTime reserveTime,TablingStatus status);

    List<TablingEntity> findByDateAndReserveTimeAndStatusIn(LocalDate date, LocalTime reserveTime, List<TablingStatus> statuses);

    List<TablingEntity> findByMemberIdAndStatusIn(Long memberId, List<TablingStatus> statuses);
}

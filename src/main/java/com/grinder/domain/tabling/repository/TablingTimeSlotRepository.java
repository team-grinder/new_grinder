package com.grinder.domain.tabling.repository;

import com.grinder.domain.tabling.entity.TablingTimeSlotEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TablingTimeSlotRepository extends JpaRepository<TablingTimeSlotEntity, Long> {
    Optional<TablingTimeSlotEntity> findByCafeIdAndDateAndReserveTime(
            Long cafeId, LocalDate date, LocalTime reserveTime);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT ts FROM TablingTimeSlotEntity ts " +
            "WHERE ts.cafeId = :cafeId " +
            "AND ts.date = :date " +
            "AND ts.reserveTime = :reserveTime")
    Optional<TablingTimeSlotEntity> findWithLockByCafeIdAndDateAndReserveTime(
            @Param("cafeId") Long cafeId,
            @Param("date") LocalDate date,
            @Param("reserveTime") LocalTime reserveTime
    );
    void deleteByCafeIdAndDate(Long cafeId, LocalDate date);
}

package com.grinder.domain.tabling.repository;

import com.grinder.domain.tabling.entity.TablingTimeSlotEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TablingTimeSlotRepository extends JpaRepository<TablingTimeSlotEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM TablingTimeSlotEntity r " +
            "WHERE r.cafeId = :cafeId " +
            "AND r.reservationDate = :date " +
            "AND r.timeSlot = :timeSlot")
    Optional<TablingTimeSlotEntity> findForUpdate(
            @Param("cafeId") Long cafeId,
            @Param("date") LocalDate date,
            @Param("timeSlot") LocalTime timeSlot
    );
}

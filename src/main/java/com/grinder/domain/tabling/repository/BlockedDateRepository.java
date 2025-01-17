package com.grinder.domain.tabling.repository;

import com.grinder.domain.tabling.entity.BlockedDateEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedDateRepository extends JpaRepository<BlockedDateEntity, Long> {
    boolean existsByCafeIdAndDate(Long cafeId, LocalDate date);

    List<BlockedDateEntity> findByCafeIdAndDateBetween(Long cafeId, LocalDate startDate, LocalDate endDate);
    Optional<BlockedDateEntity> findByCafeIdAndDate(Long cafeId, LocalDate date);

    void deleteByCafeIdAndDate(Long cafeId, LocalDate date);
}

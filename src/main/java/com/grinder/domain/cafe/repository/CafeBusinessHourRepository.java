package com.grinder.domain.cafe.repository;

import com.grinder.domain.cafe.entity.CafeBusinessHourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeBusinessHourRepository extends JpaRepository<CafeBusinessHourEntity, Long> {
    Optional<CafeBusinessHourEntity> findByCafeId(Long cafeId);
}

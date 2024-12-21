package com.grinder.domain.tabling.repository;

import com.grinder.domain.tabling.entity.TableCapacityEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TablingCapacityRepository extends JpaRepository<TableCapacityEntity, Long> {
    Optional<TableCapacityEntity> findByCafeId(Long cafeId);
}

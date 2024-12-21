package com.grinder.domain.cafe.repository;

import com.grinder.domain.cafe.entity.CafeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<CafeEntity, Long> {
    List<CafeEntity> findAllByName(String name);

    boolean findByName(String name);
}

package com.grinder.domain.tabling.repository;

import com.grinder.domain.tabling.entity.TablingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TablingRepository extends JpaRepository<TablingEntity, Long> {
}

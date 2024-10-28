package com.grinder.tabling.repository;

import com.grinder.tabling.entity.Tabling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TablingRepository extends JpaRepository<Tabling, Long> {
}

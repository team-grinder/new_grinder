package com.grinder.domain.member.repository;

import com.grinder.domain.member.entity.SystemAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemAdminRepository extends JpaRepository<SystemAdminEntity, Long> {
    Optional<SystemAdminEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}

package com.grinder.domain.member.repository;

import com.grinder.domain.member.entity.CafeAdminInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeAdminInfoRepository extends JpaRepository<CafeAdminInfoEntity, String> {
}

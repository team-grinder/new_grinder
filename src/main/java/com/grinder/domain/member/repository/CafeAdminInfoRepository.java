package com.grinder.domain.member.repository;

import com.grinder.domain.member.entity.CafeAdminInfoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeAdminInfoRepository extends JpaRepository<CafeAdminInfoEntity, String> {
    List<CafeAdminInfoEntity> findByCafeAdminId(String cafeAdminId);
}

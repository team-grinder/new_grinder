package com.grinder.domain.member.repository;

import com.grinder.domain.member.entity.CafeAdminMappingEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeAdminMappingRepository extends JpaRepository<CafeAdminMappingEntity, Long> {
    List<CafeAdminMappingEntity> findByCafeAdminId(String cafeAdminId);

    List<CafeAdminMappingEntity> findByCafeId(Long cafeId);

    Optional<CafeAdminMappingEntity> findByCafeAdminIdAndCafeId(String cafeAdminId, Long cafeId);

    boolean existsByCafeAdminIdAndCafeId(String cafeAdminId, Long cafeId);

    void deleteByCafeAdminIdAndCafeId(String cafeAdminId, Long cafeId);

    void deleteByCafeAdminId(String cafeAdminId);

    void deleteByCafeId(Long cafeId);
}

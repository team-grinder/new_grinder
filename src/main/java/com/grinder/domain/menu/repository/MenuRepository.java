package com.grinder.domain.menu.repository;

import com.grinder.domain.menu.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    int countByCafeId(Long cafeId);

    List<MenuEntity> findAllByCafeIdOrderBySequence(Long cafeId);

    @Modifying
    @Query("update MenuEntity m set m.lockYn = :lockYn where m.id = :menuId")
    void changeLockMenu(Long menuId, boolean lockYn);
}

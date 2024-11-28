package com.grinder.domain.menu.repository;

import com.grinder.domain.menu.entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, Long> {
    List<OptionEntity> findAllByMenuIdOrderBySequence(Long menuId);
}

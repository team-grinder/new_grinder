package com.grinder.domain.member.repository.login;

import com.grinder.domain.member.entity.LoginHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistoryEntity, Long> {
    List<LoginHistoryEntity> findByMemberIdAndIsActiveOrderByLastLoginDate(Long memberId, boolean isActive);
}
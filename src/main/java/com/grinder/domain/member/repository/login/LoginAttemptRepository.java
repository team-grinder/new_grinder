package com.grinder.domain.member.repository.login;


import com.grinder.domain.member.entity.LoginAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttemptEntity, Long> {
    Optional<LoginAttemptEntity> findByMemberId(Long memberId);
    Optional<LoginAttemptEntity> findByEmail(String email);
}
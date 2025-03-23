package com.grinder.domain.member.implement;

import com.grinder.common.model.Pages;
import com.grinder.domain.member.entity.SystemAdminEntity;
import com.grinder.domain.member.model.SystemAdmin;
import com.grinder.domain.member.model.SystemAdminSearchPage;
import com.grinder.domain.member.repository.SystemAdminQueryRepository;
import com.grinder.domain.member.repository.SystemAdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SystemAdminManager {
    private final SystemAdminRepository systemAdminRepository;
    private final SystemAdminQueryRepository systemAdminQueryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createSystemAdmin(String email,
                                  String password,
                                  String nickname) {

        String encodedPassword = passwordEncoder.encode(password);

        systemAdminRepository.save(SystemAdminEntity.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .build());
    }

    public Pages<SystemAdmin> getSystemAdmins(SystemAdminSearchPage searchPage) {
        return systemAdminQueryRepository.getSystemAdmins(searchPage);
    }

    public boolean existsByEmail(String email) {
        return systemAdminRepository.existsByEmail(email);
    }

    public SystemAdminEntity findByEmail(String email) {
        return systemAdminRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("관리자 정보가 존재하지 않습니다: " + email)
        );
    }

    public SystemAdminEntity findById(Long id) {
        return systemAdminRepository.findById(id).orElseThrow(
                () -> new RuntimeException("관리자 정보가 존재하지 않습니다: " + id)
        );
    }

    public SystemAdminEntity updateSystemAdmin(Long id,
                                               String email,
                                               String password,
                                               String nickname) {
        SystemAdminEntity systemAdmin = findById(id);
        String encodedPassword = passwordEncoder.encode(password);
        systemAdmin.update(email, encodedPassword, nickname);

        return systemAdminRepository.save(systemAdmin);
    }

    public void deleteSystemAdmin(Long id) {
        SystemAdminEntity systemAdmin = findById(id);
        systemAdminRepository.delete(systemAdmin);
    }

    public SystemAdmin getSystemAdmin(Long id) {
        return systemAdminRepository.findById(id).orElseThrow(
                () -> new RuntimeException("관리자 정보가 존재하지 않습니다: " + id)
        ).toSystemAdmin();
    }
}

package com.grinder.domain.member.service;

import com.grinder.common.model.Pages;
import com.grinder.domain.member.implement.SystemAdminManager;
import com.grinder.domain.member.model.SystemAdmin;
import com.grinder.domain.member.model.SystemAdminSearchPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SystemAdminService {
    private final SystemAdminManager systemAdminManager;

    @Transactional
    public void createSystemAdmin(SystemAdmin systemAdmin) {
        systemAdminManager.createSystemAdmin(
                systemAdmin.getEmail(),
                systemAdmin.getPassword(),
                systemAdmin.getNickname()
        );
    }

    @Transactional
    public void updateSystemAdmin(
            Long systemAdminId,
            String email,
            String password,
            String nickname
    ) {
        systemAdminManager.updateSystemAdmin(systemAdminId, email, password, nickname);
    }

    @Transactional
    public void deleteSystemAdmin(Long systemAdminId) {
        systemAdminManager.deleteSystemAdmin(systemAdminId);
    }

    public SystemAdmin getSystemAdmin(Long systemAdminId) {
        return systemAdminManager.getSystemAdmin(systemAdminId);
    }

    public Pages<SystemAdmin> getSystemAdmins(SystemAdminSearchPage searchPage) {
        return systemAdminManager.getSystemAdmins(searchPage);
    }

    public boolean existsByEmail(String email) {
        return systemAdminManager.existsByEmail(email);
    }
}

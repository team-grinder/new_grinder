package com.grinder.web.systemAdmin.admin;

import com.grinder.common.model.Pages;
import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.member.model.SystemAdmin;
import com.grinder.domain.member.model.SystemAdminSearchPage;
import com.grinder.domain.member.service.SystemAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/systemAdmin")
public class AdminController {
    private final SystemAdminService systemAdminService;

    @GetMapping("/list")
    public ResponseEntity<SuccessResult<Pages<SystemAdmin>>> getSystemAdminList(
            @ModelAttribute SystemAdminSearchPage searchPage
    ) {
        Pages<SystemAdmin> SystemAdmins = systemAdminService.getSystemAdmins(searchPage);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, SystemAdmins));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResult<Void>> deleteSystemAdmin(
            @RequestParam Long userId
    ) {
        systemAdminService.deleteSystemAdmin(userId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResult<Void>> updateSystemAdmin(
            @RequestParam Long userId,
            @RequestBody SystemAdmin user
    ) {
        systemAdminService.updateSystemAdmin(userId, user.getEmail(), user.getPassword(), user.getNickname());
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResult<Void>> createCafe(
            @RequestBody SystemAdmin request
    ) {
        systemAdminService.createSystemAdmin(request);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PostMapping("/search")
    public ResponseEntity<SuccessResult<SystemAdmin>> createCafe(
            @RequestParam Long userId
    ) {
        SystemAdmin user = systemAdminService.getSystemAdmin(userId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, user));
    }
}

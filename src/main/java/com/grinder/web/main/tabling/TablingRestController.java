package com.grinder.web.main.tabling;

import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.common.security.AuthenticatedUser;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.model.TablingStatus;
import com.grinder.domain.tabling.service.TablingService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tabling")
@RequiredArgsConstructor
@Slf4j
public class TablingRestController {
    private final TablingService tablingService;

    @PostMapping
    public ResponseEntity<Tabling> createTabling(@RequestBody TablingRegister request) {
        Tabling tabling = tablingService.createTabling(request);
        return ResponseEntity.ok(tabling);
    }

    @PatchMapping("/{tablingId}/status")
    public ResponseEntity<Void> updateTablingStatus(
            @PathVariable Long tablingId,
            @RequestParam TablingStatus status) {
        tablingService.updateTablingStatus(tablingId, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tablingId}")
    public ResponseEntity<Void> cancelTabling(
            @PathVariable Long tablingId,
            @AuthenticationPrincipal AuthenticatedUser user) {
        tablingService.cancelTabling(tablingId, user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mypage/tabling")
    public ResponseEntity<List<Tabling>> getMyTablings(
            @AuthenticationPrincipal AuthenticatedUser user,
            @RequestParam(required = false) List<TablingStatus> statuses) {
        if (user == null) {
            throw new MemberException(AuthResultEnum.INVALID_SESSION);
        }
        if (statuses == null) {
            statuses = Arrays.asList(TablingStatus.values());
        }
        List<Tabling> tablings = tablingService.getMemberTablings(user.getId(), statuses);
        return ResponseEntity.ok(tablings);
    }
}

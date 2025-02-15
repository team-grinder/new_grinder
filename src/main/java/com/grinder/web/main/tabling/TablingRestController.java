package com.grinder.web.main.tabling;

import com.grinder.common.exception.MemberException;
import com.grinder.common.model.AuthResultEnum;
import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.security.AuthenticatedUser;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.model.TablingStatus;
import com.grinder.domain.tabling.model.AvailableTime;
import com.grinder.domain.tabling.service.TablingService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<SuccessResult<List<Tabling>>> getMyTablings(
            @AuthenticationPrincipal AuthenticatedUser user) {
        if (user == null) {
            throw new MemberException(AuthResultEnum.INVALID_SESSION);
        }
        List<Tabling> tablings = tablingService.getMemberTablings(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResult.of(ResultEnum.SUCCESS, tablings));
    }

    @GetMapping("/cafe/{cafeId}/tabling-info")
    public ResponseEntity<AvailableTime> getTablingInfo(
            @PathVariable Long cafeId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(tablingService.getAvailableTime(cafeId, date));
    }
}

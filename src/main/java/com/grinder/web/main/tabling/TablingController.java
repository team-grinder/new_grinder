package com.grinder.web.main.tabling;

import com.grinder.common.security.AuthenticatedUser;
import com.grinder.common.security.common.model.MemberUserDetails;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.service.TablingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
//TODO: 사용자 인증 가져오기, 동시성 테스트
public class TablingController {
    private final TablingService tablingService;

    @PostMapping("tabling/register")
    public ResponseEntity<Tabling> createTabling(
            @RequestBody TablingRegister register,
            @AuthenticationPrincipal AuthenticatedUser user) {

        register.setMemberId(user.getId());
        log.info("현재 로그인한 유저 id={}",user.getId());
        return ResponseEntity.ok(tablingService.tryTabling(register));
    }
}

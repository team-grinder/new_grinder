package com.grinder.web.main;

import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.security.common.model.MemberUserDetails;
import com.grinder.domain.member.model.MemberSimple;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

    @GetMapping("/session/validate")
    public ResponseEntity<SuccessResult<MemberSimple>> mainPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            MemberUserDetails userDetails = (MemberUserDetails) authentication.getPrincipal();

            MemberSimple memberSimple = MemberSimple.builder()
                    .id(userDetails.getId())
                    .nickname(userDetails.getNickname())
                    .imageUrl(userDetails.getImageUrl())
                    .build();

            return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, memberSimple));
        }

        throw new DisabledException("잘못된 접근입니다.");
    }
}

package com.grinder.web.main;

import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.security.common.model.MemberUserDetails;
import com.grinder.common.security.oauth.model.OAuth2MemberDetails;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.model.MemberSimple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

    @GetMapping("/session/validate")
    public ResponseEntity<SuccessResult<MemberSimple>> validateSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MemberSimple memberSimple;

        if (authentication.getPrincipal() instanceof MemberUserDetails) {
            MemberUserDetails userDetails = (MemberUserDetails) authentication.getPrincipal();
            memberSimple = MemberSimple.builder()
                    .id(userDetails.getId())
                    .nickname(userDetails.getNickname())
                    .imageUrl(userDetails.getImageUrl())
                    .build();
        } else if (authentication.getPrincipal() instanceof OAuth2MemberDetails) {
            OAuth2MemberDetails userDetails = (OAuth2MemberDetails) authentication.getPrincipal();
            Member member = userDetails.getMember();
            memberSimple = MemberSimple.builder()
                    .id(member.getId())
                    .nickname(member.getEmail())
                    .imageUrl(null)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(SuccessResult.of(ResultEnum.UNAUTHORIZED));
        }

        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, memberSimple));
    }
}

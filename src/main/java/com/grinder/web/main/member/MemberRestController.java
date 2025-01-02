package com.grinder.web.main.member;

import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.member.model.MemberRegister;
import com.grinder.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResult<Void>> register(@ModelAttribute @Valid MemberRegister request) {
        memberService.register(request.getEmail(), request.getPassword(), request.getConfirmPassword());
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @GetMapping("/check-email")
    public ResponseEntity<SuccessResult<Boolean>> checkEmail(String email) {
        boolean result = memberService.existsByEmail(email);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, result));
    }
}

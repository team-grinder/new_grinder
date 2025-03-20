package com.grinder.web.systemAdmin.user;

import com.grinder.common.model.Pages;
import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.model.MemberCreate;
import com.grinder.domain.member.model.MemberSearchPage;
import com.grinder.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    private final MemberService memberService;

    @GetMapping("/user/list")
    public ResponseEntity<SuccessResult<Pages<Member>>> getUserList(
            @ModelAttribute MemberSearchPage searchPage
    ) {
        Pages<Member> Users = memberService.getUsers(searchPage);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, Users));
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<SuccessResult<Void>> deleteUser(
            @RequestParam Long userId
    ) {
        memberService.deleteUser(userId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PutMapping("/user/update")
    public ResponseEntity<SuccessResult<Void>> updateUser(
            @RequestParam Long userId,
            @RequestBody Member user
    ) {
        memberService.updateUser(userId, user);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PostMapping("/user/create")
    public ResponseEntity<SuccessResult<Member>> createCafe(
            @RequestBody MemberCreate request
    ) {
        Member createdUser = memberService.createUser(request);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, createdUser));
    }

    @PostMapping("/user/search")
    public ResponseEntity<SuccessResult<Member>> createCafe(
            @RequestParam Long userId
    ) {
        Member user = memberService.getUser(userId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, user));
    }
}

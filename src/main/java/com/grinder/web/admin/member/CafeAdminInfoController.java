package com.grinder.web.admin.member;

import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.member.model.CafeAdminInfo;
import com.grinder.domain.member.model.CafeAdminInfoRegister;
import com.grinder.domain.member.service.CafeAdminService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/cafe-manager")
public class CafeAdminInfoController {
    private final CafeAdminService cafeAdminService;

    @GetMapping("/{memberId}")
    public ResponseEntity<SuccessResult<CafeAdminInfo>> getCafeAdminInfo(
            @PathVariable Long memberId) {
        CafeAdminInfo result = cafeAdminService.getCafeAdminInfoByMemberId(memberId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, result));
    }

    @PostMapping("/authorize/{memberId}")
    public ResponseEntity<SuccessResult<Void>> promoteToCafeAdmin(
            @PathVariable Long memberId,
            @RequestBody @Valid CafeAdminInfoRegister request) {
        cafeAdminService.authorizeToCafeAdmin(memberId, request);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<SuccessResult<Void>> revokeCafeAdmin(
            @PathVariable Long memberId) {
        cafeAdminService.deleteCafeAdminInfo(memberId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }


}

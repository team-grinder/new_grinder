package com.grinder.web.systemAdmin.cafe;

import com.grinder.common.model.Pages;
import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.security.common.model.AdminUserDetails;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeCreate;
import com.grinder.domain.cafe.model.CafeSearchPage;
import com.grinder.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class SystemAdminController {
    private final CafeService cafeService;

    @GetMapping("/cafe/list")
    public ResponseEntity<SuccessResult<Pages<Cafe>>> getCafeList(
            @ModelAttribute CafeSearchPage searchPage,
            @AuthenticationPrincipal AdminUserDetails user) {

        if (user.getSystemAdmin() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(SuccessResult.of(ResultEnum.FORBIDDEN));
        }

        Pages<Cafe> cafes = cafeService.getCafes(searchPage);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, cafes));
    }

    @DeleteMapping("/cafe/delete")
    public ResponseEntity<SuccessResult<Void>> deleteCafe(
            @RequestParam Long cafeId,
            @AuthenticationPrincipal AdminUserDetails user) {

        if (user.getSystemAdmin() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(SuccessResult.of(ResultEnum.FORBIDDEN));
        }

        cafeService.deleteCafe(cafeId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PutMapping("/cafe/update")
    public ResponseEntity<SuccessResult<Void>> updateCafe(
            @RequestParam Long cafeId,
            @RequestBody Cafe cafe,
            @AuthenticationPrincipal AdminUserDetails user) {

        if (user.getSystemAdmin() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(SuccessResult.of(ResultEnum.FORBIDDEN));
        }

        cafeService.updateCafe(cafeId, cafe);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PostMapping("/cafe/create")
    public ResponseEntity<SuccessResult<Cafe>> createCafe(
            @RequestBody CafeCreate request,
            @AuthenticationPrincipal AdminUserDetails user) {

        if (user.getSystemAdmin() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(SuccessResult.of(ResultEnum.FORBIDDEN));
        }

        Cafe createdCafe = cafeService.createCafe(request);

        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, createdCafe));
    }
}

package com.grinder.web.systemAdmin.cafe;

import com.grinder.common.model.Pages;
import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.security.common.model.AdminUserDetails;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeSearchPage;
import com.grinder.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class SystemAdminController {
    private final CafeService cafeService;

    @GetMapping("/cafe-list")
    public ResponseEntity<SuccessResult<Pages<Cafe>>> getCafeList(CafeSearchPage searchPage, @AuthenticationPrincipal AdminUserDetails user) {
        if (user.getSystemAdmin() != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(SuccessResult.of(ResultEnum.FORBIDDEN));
        }

        Pages<Cafe> cafes = cafeService.getCafes(searchPage);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, cafes));
    }
}

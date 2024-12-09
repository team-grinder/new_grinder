package com.grinder.web.main.cafe;

import com.grinder.common.model.ReslutEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.cafe.model.CafeAndMenu;
import com.grinder.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe")
public class CafeController {
    private final CafeService cafeService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResult<CafeAndMenu>> getCafe(
            @PathVariable Long id) {
        CafeAndMenu cafeAndMenu = cafeService.getCafeAndMenu(id);
        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS, cafeAndMenu));
    }
}

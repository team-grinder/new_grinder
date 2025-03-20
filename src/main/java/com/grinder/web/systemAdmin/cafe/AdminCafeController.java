package com.grinder.web.systemAdmin.cafe;

import com.grinder.common.model.Pages;
import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeCreate;
import com.grinder.domain.cafe.model.CafeSearchPage;
import com.grinder.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCafeController {
    private final CafeService cafeService;

    @GetMapping("/cafe/list")
    public ResponseEntity<SuccessResult<Pages<Cafe>>> getCafeList(
            @ModelAttribute CafeSearchPage searchPage
    ) {
        Pages<Cafe> cafes = cafeService.getCafes(searchPage);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, cafes));
    }

    @DeleteMapping("/cafe/delete")
    public ResponseEntity<SuccessResult<Void>> deleteCafe(
            @RequestParam Long cafeId
    ) {
        cafeService.deleteCafe(cafeId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PutMapping("/cafe/update")
    public ResponseEntity<SuccessResult<Void>> updateCafe(
            @RequestParam Long cafeId,
            @RequestBody Cafe cafe
    ) {
        cafeService.updateCafe(cafeId, cafe);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS));
    }

    @PostMapping("/cafe/create")
    public ResponseEntity<SuccessResult<Cafe>> createCafe(
            @RequestBody CafeCreate request
    ) {
        Cafe createdCafe = cafeService.createCafe(request);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, createdCafe));
    }

    @PostMapping("/cafe/search")
    public ResponseEntity<SuccessResult<Cafe>> createCafe(
            @RequestParam Long cafeId
    ) {
        Cafe cafe = cafeService.getCafe(cafeId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, cafe));
    }
}

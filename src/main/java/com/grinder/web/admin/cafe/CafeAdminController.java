package com.grinder.web.admin.cafe;

import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeBusinessInfo;
import com.grinder.domain.cafe.model.CafeBusinessInfoRegister;
import com.grinder.domain.cafe.model.CafeCreate;
import com.grinder.domain.cafe.service.CafeService;
import com.grinder.domain.member.model.CafeAdminInfo;
import com.grinder.domain.member.service.CafeAdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cafe-manager")
public class CafeAdminController {
    private final CafeService cafeService;
    private final CafeAdminService cafeAdminService;

    @PostMapping("/cafe/create")
    public ResponseEntity<Cafe> createCafe(@RequestBody CafeCreate request) {
        Cafe cafe = cafeService.createCafeAndBusinessHour(request);
        return ResponseEntity.ok(cafe);
    }
  
    @GetMapping("/{memberId}")
    public ResponseEntity<SuccessResult<List<CafeAdminInfo>>> getCafeAdminInfo(
            @PathVariable Long memberId) {
        List<CafeAdminInfo> results =cafeAdminService.getCafeAdminInfoByMemberId(memberId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, results));
    }

    @GetMapping("/{memberId}/cafes")
    public ResponseEntity<SuccessResult<List<Cafe>>> getManagedCafes(
            @PathVariable Long memberId) {
        List<Cafe> cafes = cafeAdminService.getManagedCafes(memberId);
        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, cafes));
    }

    @PostMapping("/cafe/{cafeId}/business-hours")
    public ResponseEntity<CafeBusinessInfo> setBusinessHours(
            @PathVariable Long cafeId,
            @RequestBody CafeBusinessInfoRegister request) {
        return ResponseEntity.ok(cafeService.setBusinessHours(cafeId, request));
    }

    @PutMapping("/cafe/{cafeId}/business-hours")
    public ResponseEntity<CafeBusinessInfo> updateBusinessHours(
            @PathVariable Long cafeId,
            @RequestBody CafeBusinessInfoRegister request) {
        CafeBusinessInfo updatedInfo = cafeService.updateBusinessHours(cafeId, request);
        return ResponseEntity.ok(updatedInfo);
    }

}

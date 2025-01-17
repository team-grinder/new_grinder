package com.grinder.web.admin.cafe;

import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeCreate;
import com.grinder.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CafeAdminController {
    private final CafeService cafeService;

    @PostMapping("/cafe/create")
    public ResponseEntity<Cafe> createCafe(@RequestBody CafeCreate request) {
        Cafe cafe = cafeService.createCafe(
                request.getName(),
                request.getAddress(),
                request.getDescription(),
                request.getTel(),
                request.getBusinessNumber()
        );
        return ResponseEntity.ok(cafe);
    }
}

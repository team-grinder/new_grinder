package com.grinder.web.systemAdmin.cafe;

import com.grinder.common.model.Pages;
import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class SystemAdminController {
    private final CafeService cafeService;

    @GetMapping("/cafe-list")
    public ResponseEntity<Pages<Cafe>> getCafeList() {

        return null;
    }
}

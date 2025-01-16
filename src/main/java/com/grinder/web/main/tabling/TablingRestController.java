package com.grinder.web.main.tabling;

import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.model.TablingStatus;
import com.grinder.domain.tabling.service.TablingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tabling")
@RequiredArgsConstructor
@Slf4j
public class TablingRestController {
    private final TablingService tablingService;

    @PostMapping
    public ResponseEntity<Tabling> createTabling(@RequestBody TablingRegister request) {
        Tabling tabling = tablingService.createTabling(request);
        return ResponseEntity.ok(tabling);
    }

    @PatchMapping("/{tablingId}/status")
    public ResponseEntity<Void> updateTablingStatus(
            @PathVariable Long tablingId,
            @RequestParam TablingStatus status) {
        tablingService.updateTablingStatus(tablingId, status);
        return ResponseEntity.ok().build();
    }
}

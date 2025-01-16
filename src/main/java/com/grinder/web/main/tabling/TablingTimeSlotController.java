package com.grinder.web.main.tabling;

import com.grinder.domain.tabling.model.TimeSlotSetting;
import com.grinder.domain.tabling.service.TablingTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tabling/time-slots")
@RequiredArgsConstructor
public class TablingTimeSlotController {
    private final TablingTimeSlotService tablingTimeSlotService;

    @PostMapping("/{cafeId}")
    public ResponseEntity<Void> setTimeSlots(
            @PathVariable Long cafeId,
            @RequestBody TimeSlotSetting request) {
        tablingTimeSlotService.setTimeSlots(cafeId, request.getDate(), request.getTimeSlots()
        );
        return ResponseEntity.ok().build();
    }
}
package com.grinder.web.main.tabling;

import com.grinder.domain.tabling.model.AvailableTime;
import com.grinder.domain.tabling.model.TimeSlotSetting;
import com.grinder.domain.tabling.service.TablingService;
import com.grinder.domain.tabling.service.TablingTimeSlotService;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tabling/time-slots")
@RequiredArgsConstructor
public class TablingTimeSlotController {
    private final TablingTimeSlotService tablingTimeSlotService;
    private final TablingService tablingService;
    @PostMapping("/{cafeId}")
    public ResponseEntity<Void> setTimeSlots(
            @PathVariable Long cafeId,
            @RequestBody TimeSlotSetting request) {
        tablingTimeSlotService.setTimeSlots(cafeId, request.getDate(), request.getTimeSlots()
        );
        return ResponseEntity.ok().build();
    }
    @Transactional
    @PutMapping("/{cafeId}")
    public ResponseEntity<Void> updateTimeSlots(
            @PathVariable Long cafeId,
            @RequestBody TimeSlotSetting request) {
        tablingTimeSlotService.updateTimeSlots(
                cafeId,
                request.getDate(),
                request.getTimeSlots()
        );
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{cafeId}")
    public ResponseEntity<List<Map<String, Object>>> getTimeSlots(
            @PathVariable Long cafeId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        AvailableTime availableTime = tablingService.getAvailableTime(cafeId, date);
        return ResponseEntity.ok(availableTime.toMapList());
    }
}
package com.grinder.web.admin.tabling;

import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingStatus;
import com.grinder.domain.tabling.model.TimeSlotSetting;
import com.grinder.domain.tabling.service.TablingService;
import com.grinder.domain.tabling.service.TablingTimeSlotService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/tabling")
@RequiredArgsConstructor
public class TablingAdminController {
    private final TablingService tablingService;
    private final TablingTimeSlotService tablingTimeSlotService;

//    @PostMapping("/time-slots/{cafeId}")
//    public ResponseEntity<Void> setTimeSlots(
//            @PathVariable Long cafeId,
//            @RequestBody TimeSlotSetting request) {
//        tablingTimeSlotService.setTimeSlots(
//                cafeId,
//                request.getDate(),
//                request.getTimeSlots()
//        );
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/cafes/{cafeId}/date")
//    public ResponseEntity<List<Tabling>> getCafeTablings(
//            @PathVariable Long cafeId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        List<Tabling> tablings = tablingService.getCafeTablings(cafeId, date);
//        return ResponseEntity.ok(tablings);
//    }
//
//    @GetMapping("/cafes/{cafeId}/period")
//    public ResponseEntity<List<Tabling>> getCafeTablingsBetween(
//            @PathVariable Long cafeId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        List<Tabling> tablings = tablingService.getCafeTablingsBetween(cafeId, startDate, endDate);
//        return ResponseEntity.ok(tablings);
//    }
//
//    @GetMapping("/cafes/{cafeId}/status")
//    public ResponseEntity<List<Tabling>> getCafeTablingsByStatus(
//            @PathVariable Long cafeId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
//            @RequestParam List<TablingStatus> statuses) {
//        List<Tabling> tablings = tablingService.getCafeTablingsByStatus(cafeId, date, statuses);
//        return ResponseEntity.ok(tablings);
//    }
//
//    @GetMapping("/time-slots")
//    public ResponseEntity<List<Tabling>> getTimeSlotTablings(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
//            @RequestParam(required = false) List<TablingStatus> statuses) {
//        if (statuses == null) {
//            statuses = Arrays.asList(TablingStatus.values());
//        }
//        List<Tabling> tablings = tablingService.getTimeSlotTablings(date, time, statuses);
//        return ResponseEntity.ok(tablings);
//    }
}

package com.grinder.web.main.tabling;

import com.grinder.common.model.ReslutEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.common.security.AuthenticatedUser;
import com.grinder.common.security.common.model.MemberUserDetails;
import com.grinder.domain.tabling.model.CapacityRegister;
import com.grinder.domain.tabling.model.TableCapacity;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingRegister;
import com.grinder.domain.tabling.model.TablingTimeSlot;
import com.grinder.domain.tabling.model.TimeSlotsRegister;
import com.grinder.domain.tabling.service.TableCapacityService;
import com.grinder.domain.tabling.service.TablingService;
import com.grinder.domain.tabling.service.TablingTimeSlotService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
//TODO: 사용자 인증 가져오기, 동시성 테스트
public class TablingController {
    private final TablingService tablingService;
    private final TableCapacityService tableCapacityService;
    private final TablingTimeSlotService tablingTimeSlotService;

    @PostMapping("tabling/register")
    public ResponseEntity<SuccessResult<Tabling>> createTabling(
            @RequestBody TablingRegister register,
            @AuthenticationPrincipal AuthenticatedUser user) {

        register.setMemberId(user.getId());
        log.info("현재 로그인한 유저 id={}",user.getId());
        Tabling tabling = tablingService.tryTabling(register);
        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS,tabling));
    }

    @PostMapping("/cafe/{cafeId}/capacity")
    public ResponseEntity<SuccessResult<TableCapacity>> createCapacity(
            @PathVariable Long cafeId,
            @RequestBody CapacityRegister register) {
        TableCapacity capacity = tableCapacityService.createCapacity(
                cafeId,
                register.getMinCapacity(),
                register.getMaxCapacity()
        );
        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS, capacity));
    }

    @GetMapping("/cafe/{cafeId}/capacity")
    public ResponseEntity<SuccessResult<TableCapacity>> getCapacity(
            @PathVariable Long cafeId) {
        TableCapacity capacity = tableCapacityService.getCapacity(cafeId);
        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS, capacity));
    }

    @PostMapping("/cafe/{cafeId}/timeslots")
    public ResponseEntity<SuccessResult<Void>> createTimeSlots(
            @PathVariable Long cafeId,
            @RequestBody TimeSlotsRegister register) {
        tablingTimeSlotService.createTimeSlots(
                cafeId,
                register.getDate(),
                register.getOpenTime(),
                register.getCloseTime(),
                register.getMaxReservations()
        );
        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS, null));
    }

    @GetMapping("/cafe/{cafeId}/timeslots")
    public ResponseEntity<SuccessResult<List<TablingTimeSlot>>> getAvailableTimeSlots(
            @PathVariable Long cafeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<TablingTimeSlot> slots = tablingTimeSlotService.getAvailableTimeSlots(cafeId, date);
        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS, slots));
    }
}

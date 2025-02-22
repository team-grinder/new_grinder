package com.grinder;

import com.grinder.domain.cafe.service.CafeService;
import com.grinder.domain.member.service.MemberService;
import com.grinder.domain.tabling.service.TablingTimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateDummy {
    @Autowired
    private MemberService memberService;

    @Autowired
    private TablingTimeSlotService tablingTimeSlotService;

    @Autowired
    private CafeService cafeService;

    @Transactional
    public void createMemberDummy(int count) {
        for (int i = 0; i < count; i++) {
            String email = "test" + i + "@test.com";
            String password = "test" + i;
            String confirmPassword = "test" + i;
            String nickname = "test" + i;
            memberService.register(email, password, nickname, confirmPassword);
        }
    }

    @Transactional
    public void createCafeDummy(int count) {
        for (int i = 0; i < count; i++) {
            String name = "카페" + i;
            String address = "서울시 강남구 테헤란로" + i + "길";
            String phone = "010-1234-5678";
            String businessNumber = "123-45-67890";
            String description = "카페" + i + "입니다.";
           // cafeService.createCafe(name, address, phone, businessNumber, description);
        }
    }


//    @Transactional
//    public void createTableCapacityDummy(Long cafeId, int minCapacity, int maxCapacity) {
//        tableCapacityService.createCapacity(cafeId, minCapacity, maxCapacity);
//    }

//    @Transactional
//    public void createTimeSlotDummy(Long cafeId, LocalTime openTime, LocalTime closeTime, int maxReservations) {
//        tablingTimeSlotService.createTimeSlots(
//                cafeId,
//                LocalDate.now(),
//                openTime,
//                closeTime,
//                maxReservations
//        );
//    }

//    @Transactional
//    public void createFullDummy(int memberCount, int cafeCount) {
//        createMemberDummy(memberCount);
//        createCafeDummy(cafeCount);
//
//        for (long i = 1; i <= cafeCount; i++) {
//            createTableCapacityDummy(i, 2, 6);
//            createTimeSlotDummy(
//                    i,
//                    LocalTime.of(9, 0),
//                    LocalTime.of(22, 0),
//                    5
//            );
//        }
//    }
}

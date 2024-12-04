package com.grinder;

import com.grinder.domain.cafe.service.CafeService;
import com.grinder.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateDummy {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CafeService cafeService;

    @Transactional
    public void createMemberDummy(int count) {
        for (int i = 0; i < count; i++) {
            String email = "test" + i + "@test.com";
            String password = "test" + i;
            String confirmPassword = "test" + i;
            memberService.register(email, password, confirmPassword);
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
            cafeService.createCafe(name, address, phone, businessNumber, description);
        }
    }
}

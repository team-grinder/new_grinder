package com.grinder.web;

import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.service.CafeService;
import com.grinder.domain.feed.service.FeedService;
import com.grinder.domain.member.implement.MemberManager;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("local")
@RequiredArgsConstructor
public class GrinderLocalAppRunner implements ApplicationRunner {
    private final MemberManager memberManager;
    private final MemberService memberService;
    private final CafeService cafeService;
    private final FeedService feedService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (!memberService.existsByEmail("admin")) {
            Member save = memberManager.save("admin", "test", "admin");
        }

        if (cafeService.getPopularCafe().isEmpty()) {
            Cafe cafe = cafeService.createCafe("스타벅스 강남점", "서울 강남구 역삼동 23-1", "강남에 있는 카페", "02-1234-5678", "123-456-789");
            Cafe cafe1 = cafeService.createCafe("스타벅스 홍대점", "서울 마포구 서교동 395-166", "홍대에 있는 카페", "02-8765-4321", "987-654-321");
            Cafe cafe2 = cafeService.createCafe("스타벅스 신촌점", "서울 서대문구 창천동 56-1", "신촌에 있는 카페", "02-1111-2222", "111-222-333");
            Cafe cafe3 = cafeService.createCafe("스타벅스 이대점", "서울 마포구 노고산동 56-1", "이대에 있는 카페", "02-3333-4444", "333-444-555");
            Cafe cafe4 = cafeService.createCafe("스타벅스 건대점", "서울 광진구 화양동 56-1", "건대에 있는 카페", "02-5555-6666", "555-666-777");
        }


    }
}

package com.grinder.web;

import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.service.CafeService;
import com.grinder.domain.feed.model.CreateFeedRequest;
import com.grinder.domain.feed.repository.FeedRepository;
import com.grinder.domain.feed.service.FeedService;
import com.grinder.domain.member.implement.MemberManager;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Component
@Profile("local")
@RequiredArgsConstructor
public class GrinderLocalAppRunner implements ApplicationRunner {
    private final MemberManager memberManager;
    private final MemberService memberService;
    private final CafeService cafeService;
    private final FeedService feedService;
    private final FeedRepository feedRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Long memberId;
        if (!memberService.existsByEmail("admin")) {
            Member member = memberManager.save("admin", "test", "admin");
            memberId = member.getId();
        } else {
            memberId = memberManager.findByEmail("admin").getId();
        }

        Long CafeId;
        if (cafeService.getPopularCafe().isEmpty()) {
            Cafe cafe = cafeService.createCafe("스타벅스 강남점", "서울 강남구 역삼동 23-1", "강남에 있는 카페", "02-1234-5678", "123-456-789");
            Cafe cafe1 = cafeService.createCafe("스타벅스 홍대점", "서울 마포구 서교동 395-166", "홍대에 있는 카페", "02-8765-4321", "987-654-321");
            Cafe cafe2 = cafeService.createCafe("스타벅스 신촌점", "서울 서대문구 창천동 56-1", "신촌에 있는 카페", "02-1111-2222", "111-222-333");

            CafeId = cafe.getId();
        } else {
            CafeId = cafeService.getPopularCafe().get(0).getId();
        }

        if (feedRepository.findAll().isEmpty()) {
            for (int i = 0; i < 20; i++) {
                CreateFeedRequest createFeedRequest = new CreateFeedRequest(memberId, CafeId, "스타벅스 강남점 너무 좋아요", 5, 1, true, List.of());
                feedService.createFeed(createFeedRequest, memberId);
            }
        }
    }
}

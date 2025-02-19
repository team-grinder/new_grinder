package com.grinder.web;

import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeCreate;
import com.grinder.domain.cafe.service.CafeService;
import com.grinder.domain.feed.model.CreateFeedRequest;
import com.grinder.domain.feed.repository.FeedRepository;
import com.grinder.domain.feed.service.FeedService;
import com.grinder.domain.member.implement.MemberManager;
import com.grinder.domain.member.model.Member;
import com.grinder.domain.member.service.MemberService;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Component
@Profile("local")
@RequiredArgsConstructor
@Order(1)
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
            CafeCreate cafeRequest = CafeCreate.builder()
                    .name("스타벅스 강남점")
                    .address("서울 강남구 역삼동 23-1")
                    .description("강남에 있는 카페")
                    .tel("02-1234-5678")
                    .businessNumber("123-456-789")
                    .startTime(LocalTime.of(9, 0))
                    .endTime(LocalTime.of(22, 0))
                    .maxTimePerReservation(120)
                    .maxGuestsPerTime(10)
                    .blockedTimes(List.of(
                            LocalTime.of(12, 0),
                            LocalTime.of(15, 0)
                    ))
                    .build();
            Cafe cafe = cafeService.createCafe(cafeRequest);

            CafeCreate cafeRequest1 = CafeCreate.builder()
                    .name("스타벅스 홍대점")
                    .address("서울 마포구 서교동 395-166")
                    .description("홍대에 있는 카페")
                    .tel("02-8765-4321")
                    .businessNumber("987-654-321")
                    .startTime(LocalTime.of(10, 0))
                    .endTime(LocalTime.of(21, 0))
                    .maxTimePerReservation(90)
                    .maxGuestsPerTime(8)
                    .blockedTimes(List.of(
                            LocalTime.of(13, 0),
                            LocalTime.of(16, 0)
                    ))
                    .build();
            Cafe cafe1 = cafeService.createCafe(cafeRequest1);

            CafeCreate cafeRequest2 = CafeCreate.builder()
                    .name("스타벅스 신촌점")
                    .address("서울 서대문구 창천동 56-1")
                    .description("신촌에 있는 카페")
                    .tel("02-1111-2222")
                    .businessNumber("111-222-333")
                    .startTime(LocalTime.of(8, 0))
                    .endTime(LocalTime.of(23, 0))
                    .maxTimePerReservation(60)
                    .maxGuestsPerTime(12)
                    .blockedTimes(List.of(
                            LocalTime.of(14, 0),
                            LocalTime.of(17, 0)
                    ))
                    .build();
            Cafe cafe2 = cafeService.createCafe(cafeRequest2);

            CafeId = cafe.getId();
        } else {
            CafeId = cafeService.getPopularCafe().get(0).getId();
        }

        if (feedRepository.findAll().isEmpty()) {
            for (int i = 0; i < 20; i++) {
                CreateFeedRequest createFeedRequest = new CreateFeedRequest(memberId, 2L, CafeId, "스타벅스 강남점 너무 좋아요", 5, 1, true, List.of());
                feedService.createFeed(createFeedRequest, memberId);
            }
        }
    }
}

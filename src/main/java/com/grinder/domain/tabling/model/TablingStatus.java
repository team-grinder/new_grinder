package com.grinder.domain.tabling.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TablingStatus {
    PENDING("예약 대기중"),
    CONFIRMED("예약 확정"),
    CANCEL("예약 취소"),
    COMPLETED("이용 완료, 리뷰 미작성"),
    COMPLETED_REVIEW("이용 및 리뷰 완료");

    private final String description;

    public static List<TablingStatus> getAll() {
        return List.of(values());
    }
}

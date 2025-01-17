package com.grinder.domain.tabling.model;

public enum TablingStatus {
    PENDING("예약 대기중"),
    CONFIRMED("예약 확정"),
    CANCEL("예약 취소"),
    COMPLETED("이용 완료");

    private final String description;

    TablingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

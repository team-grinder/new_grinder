package com.grinder.common.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Slices<T> {
    // 필드: 현재 페이지, 페이지당 항목 수, SQL start/end, 다음 페이지 여부
    private final int nowPage;
    private final int cntPerPage;
    private final int start;
    private final int end;
    private final boolean hasNext;

    private final List<T> content;

    private Slices(List<T> content, int nowPage, int cntPerPage) {
        this.content = content;
        this.nowPage = nowPage;
        this.cntPerPage = cntPerPage;
        int[] startEnd = calculateStartEnd();
        this.start = startEnd[0];
        this.end = startEnd[1];

        // 다음 페이지가 있는지 여부는 조회된 데이터 수로 판별
        this.hasNext = content.size() > cntPerPage;

        // 데이터 크기가 페이지 크기를 초과하면 초과 항목 제거 (hasNext 판별 후)
        if (this.hasNext) {
            this.content.remove(this.content.size() - 1);
        }
    }

    // SQL 쿼리에서 사용할 start, end 값 계산
    private int[] calculateStartEnd() {
        int end = nowPage * cntPerPage;
        int start = end - cntPerPage + 1;
        return new int[]{start, end};
    }

    // 슬라이스 정보 생성 메서드 (정적 팩토리 메서드 사용)
    public static <T> Slices<T> create(List<T> content, int nowPage, int cntPerPage) {
        return new Slices<>(content, nowPage, cntPerPage);
    }
}
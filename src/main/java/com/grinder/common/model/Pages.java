package com.grinder.common.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Pages<T> {
    // 필드: 현재 페이지, 시작 페이지, 끝 페이지, 전체 항목 수, 페이지당 항목 수, 마지막 페이지, SQL start/end
    private final long nowPage;
    private final long startPage;
    private final long endPage;
    private final long total;
    private final long cntPerPage;
    private final long lastPage;
    private final long start;
    private final long end;

    // 페이지당 보여줄 페이지 수
    private final long cntPage;

    // 컨텐츠 필드
    private final List<T> content;

    public Pages(List<T> content, long total, long nowPage, long cntPerPage, long cntPage) {
        this.content = content;
        this.total = total;
        this.nowPage = nowPage;
        this.cntPerPage = cntPerPage;
        this.cntPage = cntPage;
        this.lastPage = calculateLastPage();
        long[] startEndPage = calculateStartEndPage();
        this.startPage = startEndPage[0];
        this.endPage = startEndPage[1];
        long[] startEnd = calculateStartEnd();
        this.start = startEnd[0];
        this.end = startEnd[1];
    }

    // 제일 마지막 페이지 계산
    private long calculateLastPage() {
        return (long) Math.ceil((double) total / cntPerPage);
    }

    // 시작 페이지, 끝 페이지 계산
    private long[] calculateStartEndPage() {
        long endPage = ((long) Math.ceil((double) nowPage / cntPage)) * cntPage;
        if (endPage > lastPage) {
            endPage = lastPage;
        }
        long startPage = Math.max(endPage - cntPage + 1, 1);
        return new long[]{startPage, endPage};
    }

    // SQL 쿼리에서 사용할 start, end 값 계산
    private long[] calculateStartEnd() {
        long end = nowPage * cntPerPage;
        long start = end - cntPerPage + 1;
        return new long[]{start, end};
    }

    // 페이지 정보 생성 메서드 (정적 팩토리 메서드 사용)
    public static <T> Pages<T> create(List<T> content, long total, long nowPage, long cntPerPage, long cntPage) {
        return new Pages<>(content, total, nowPage, cntPerPage, cntPage);
    }
}

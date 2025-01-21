package com.grinder.common.model;

import lombok.Getter;

@Getter
public class Pages {
    // 필드: 현재 페이지, 시작 페이지, 끝 페이지, 전체 항목 수, 페이지당 항목 수, 마지막 페이지, SQL start/end
    private final int nowPage;
    private final int startPage;
    private final int endPage;
    private final int total;
    private final int cntPerPage;
    private final int lastPage;
    private final int start;
    private final int end;

    // 페이지당 보여줄 페이지 수
    private final int cntPage;

    public Pages(int total, int nowPage, int cntPerPage, int cntPage) {
        this.total = total;
        this.nowPage = nowPage;
        this.cntPerPage = cntPerPage;
        this.cntPage = cntPage;
        this.lastPage = calculateLastPage();
        int[] startEndPage = calculateStartEndPage();
        this.startPage = startEndPage[0];
        this.endPage = startEndPage[1];
        int[] startEnd = calculateStartEnd();
        this.start = startEnd[0];
        this.end = startEnd[1];
    }

    // 제일 마지막 페이지 계산
    private int calculateLastPage() {
        return (int) Math.ceil((double) total / cntPerPage);
    }

    // 시작 페이지, 끝 페이지 계산
    private int[] calculateStartEndPage() {
        int endPage = ((int) Math.ceil((double) nowPage / cntPage)) * cntPage;
        if (endPage > lastPage) {
            endPage = lastPage;
        }
        int startPage = Math.max(endPage - cntPage + 1, 1);
        return new int[]{startPage, endPage};
    }

    // SQL 쿼리에서 사용할 start, end 값 계산
    private int[] calculateStartEnd() {
        int end = nowPage * cntPerPage;
        int start = end - cntPerPage + 1;
        return new int[]{start, end};
    }

    // 페이지 정보 생성 메서드 (정적 팩토리 메서드 사용)
    public static Pages create(int total, int nowPage, int cntPerPage, int cntPage) {
        return new Pages(total, nowPage, cntPerPage, cntPage);
    }
}

package com.grinder.domain.member.model;

import com.grinder.common.model.PageVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchPage extends PageVO {

    public enum SearchType {
        ALL, EMAIL, NICKNAME, PHONE
    }

    private SearchType searchType;
    private String searchQuery;
    private String startDate;
    private String endDate;

    public MemberSearchPage(long page, long size) {
        super(page, size);
    }

    public MemberSearchPage(long page, long size, SearchType searchType, String searchQuery, String startDate, String endDate) {
        super(page, size);
        this.searchType = searchType;
        this.searchQuery = searchQuery;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
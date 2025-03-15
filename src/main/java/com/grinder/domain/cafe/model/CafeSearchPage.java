package com.grinder.domain.cafe.model;

import com.grinder.common.model.PageVO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CafeSearchPage extends PageVO {

    public enum SearchType {
        ALL, NAME, ADDRESS
    }

    private SearchType searchType;
    private String searchKeyword;
    private String startDate;
    private String endDate;

    public CafeSearchPage(long page, long size) {
        super(page, size);
    }

    public CafeSearchPage(long page, long size, SearchType searchType, String searchKeyword, String startDate, String endDate) {
        super(page, size);
        this.searchType = searchType;
        this.searchKeyword = searchKeyword;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

package com.grinder.domain.cafe.model;

import com.grinder.common.model.PageVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CafeSearchPage extends PageVO {

    public enum SearchType {
        ALL, NAME, ADDRESS
    }

    private SearchType searchType;
    private String searchQuery;
    private String startDate;
    private String endDate;

    public CafeSearchPage(long page, long size) {
        super(page, size);
    }

    public CafeSearchPage(long page, long size, SearchType searchType, String searchQuery, String startDate, String endDate) {
        super(page, size);
        this.searchType = searchType;
        this.searchQuery = searchQuery;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

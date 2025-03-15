package com.grinder.domain.cafe.model;

import com.grinder.common.model.PageVO;
import lombok.Getter;

@Getter
public class CafeSearchPage extends PageVO {
    private String searchType;
    private String searchKeyword;

    public CafeSearchPage() {
        super();
    }

    public CafeSearchPage(int page, int size) {
        super(page, size);
    }

    public CafeSearchPage(int page, int size, String searchType, String searchKeyword) {
        super(page, size);
        this.searchType = searchType;
        this.searchKeyword = searchKeyword;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
}

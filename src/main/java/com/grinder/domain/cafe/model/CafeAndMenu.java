package com.grinder.domain.cafe.model;

import com.grinder.domain.menu.model.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CafeAndMenu {
    private String name;

    private String address;

    private String imageUrl;

    private String logoUrl;

    private String phoneNum;

    private int averageGrade;

    private final List<Menu> menus;
}

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
    private final Cafe cafe;
    private final List<Menu> menus;
}

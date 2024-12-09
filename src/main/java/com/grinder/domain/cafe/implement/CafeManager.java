package com.grinder.domain.cafe.implement;

import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.cafe.model.CafeAndMenu;
import com.grinder.domain.menu.implement.MenuReader;
import com.grinder.domain.menu.model.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CafeManager {
    private final CafeReader cafeReader;
    private final MenuReader menuReader;

    public CafeAndMenu getCafeAndMenu(Long cafeId) {
        Cafe cafe = cafeReader.read(cafeId);
        List<Menu> menus = menuReader.readAllMenu(cafeId);

        return CafeAndMenu.builder()
                .cafe(cafe)
                .menus(menus)
                .build();
    }
}

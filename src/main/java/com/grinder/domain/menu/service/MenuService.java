package com.grinder.domain.menu.service;

import com.grinder.domain.menu.implement.MenuManager;
import com.grinder.domain.menu.implement.MenuReader;
import com.grinder.domain.menu.model.Menu;
import com.grinder.domain.menu.model.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {
    private final MenuReader menuReader;
    private final MenuManager menuManager;

    public List<Menu> getMenuList(Long cafeId) {
        return menuReader.readAllMenu(cafeId);
    }

    public List<Option> getOptionList(Long menuId) {
        return menuManager.getOptions(menuId);
    }
}

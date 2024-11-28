package com.grinder.web.main.menu;

import com.grinder.domain.menu.model.Menu;
import com.grinder.domain.menu.model.Option;
import com.grinder.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    public String getMenuList(Long cafeId, Model model) {
        List<Menu> menuList = menuService.getMenuList(cafeId);

        model.addAttribute("menuList", menuList);

        return "menu/menuList";
    }

    public String getMenuOptions(Long menuId, Model model) {
        List<Option> optionList = menuService.getOptionList(menuId);

        model.addAttribute("optionList", optionList);

        return "menu/menuOptions";
    }
}

package com.grinder.web.main.menu;

import com.grinder.common.model.ReslutEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.menu.model.Menu;
import com.grinder.domain.menu.model.Option;
import com.grinder.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    public ResponseEntity<SuccessResult<List<Menu>>>
    getMenuList(Long cafeId) {
        List<Menu> menuList = menuService.getMenuList(cafeId);

        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS, menuList));
    }

    public ResponseEntity<SuccessResult<List<Option>>>
    getMenuOptions(Long menuId) {
        List<Option> optionList = menuService.getOptionList(menuId);

        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS, optionList));
    }
}

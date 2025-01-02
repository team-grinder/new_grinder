package com.grinder.web.main.menu;

import com.grinder.common.model.ResultEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.menu.model.Menu;
import com.grinder.domain.menu.model.Option;
import com.grinder.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/list/{cafeId}")
    public ResponseEntity<SuccessResult<List<Menu>>> getMenuList(
            @PathVariable Long cafeId) {
        List<Menu> menuList = menuService.getMenuList(cafeId);

        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, menuList));
    }

    @GetMapping("/options/{menuId}")
    public ResponseEntity<SuccessResult<List<Option>>> getMenuOptions(
            @PathVariable Long menuId) {
        List<Option> optionList = menuService.getOptionList(menuId);

        return ResponseEntity.ok(SuccessResult.of(ResultEnum.SUCCESS, optionList));
    }
}

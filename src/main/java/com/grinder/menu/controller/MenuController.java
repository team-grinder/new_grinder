package com.grinder.menu.controller;

import com.grinder.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
}

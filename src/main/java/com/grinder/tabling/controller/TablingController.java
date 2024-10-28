package com.grinder.tabling.controller;

import com.grinder.tabling.service.TablingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TablingController {
    private final TablingService tablingService;
}

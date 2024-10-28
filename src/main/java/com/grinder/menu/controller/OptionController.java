package com.grinder.menu.controller;

import com.grinder.menu.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OptionController {
    public final OptionService optionService;
}

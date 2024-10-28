package com.grinder.cafe.controller;

import com.grinder.cafe.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CafeController {
    private final CafeService cafeService;
}

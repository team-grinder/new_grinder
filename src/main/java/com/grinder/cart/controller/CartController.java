package com.grinder.cart.controller;

import com.grinder.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CartController {
    public final CartService CartService;
}

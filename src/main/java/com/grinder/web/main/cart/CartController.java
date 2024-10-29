package com.grinder.web.main.cart;

import com.grinder.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CartController {
    public final CartService CartService;
}

package com.grinder.web.main.cart;

import com.grinder.domain.cart.model.CartInformation;
import com.grinder.domain.cart.model.MenuVO;
import com.grinder.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    public final CartService CartService;

    @GetMapping("/myCart")
    public String myCart(@AuthenticationPrincipal UserDetails userDetails,
                         Model model) {
        String email = userDetails.getUsername();
        CartInformation myCart = CartService.getMyCart(email);

        model.addAttribute("myCart", myCart);

        return "";
    }

    @PostMapping("/addMenu")
    public String addMenuToCart(@AuthenticationPrincipal UserDetails userDetails,
                                MenuVO menuVO) {
        String email = userDetails.getUsername();

        CartService.addMenuToCart(email, menuVO);

        return "";
    }
}

package com.grinder.web.main.cart;

import com.grinder.common.model.ReslutEnum;
import com.grinder.common.model.SuccessResult;
import com.grinder.domain.cart.model.CartInformation;
import com.grinder.domain.cart.model.MenuVO;
import com.grinder.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    public final CartService CartService;

    @GetMapping("/myCart")
    public ResponseEntity<SuccessResult<CartInformation>>
    myCart(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        CartInformation myCart = CartService.getMyCart(email);

        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS, myCart));
    }

    @PostMapping("/addMenu")
    public ResponseEntity<SuccessResult<Void>>
    addMenuToCart(@AuthenticationPrincipal UserDetails userDetails, MenuVO menuVO) {
        String email = userDetails.getUsername();

        if (!CartService.addMenuToCart(email, menuVO)) {
            throw new IllegalArgumentException("메뉴 추가에 실패했습니다.");
        }

        return ResponseEntity.ok(SuccessResult.of(ReslutEnum.SUCCESS));
    }
}

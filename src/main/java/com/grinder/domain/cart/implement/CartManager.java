package com.grinder.domain.cart.implement;

import com.grinder.domain.cart.entity.CartEntity;
import com.grinder.domain.cart.model.Cart;
import com.grinder.domain.cart.model.CartDetail;
import com.grinder.domain.menu.implement.MenuReader;
import com.grinder.domain.menu.model.MenuInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartManager {
    private final MenuReader menuReader;
    private final CartReader cartReader;

    public void createCart(Long memberId, Long cafeId) {
        if (cartReader.hasUnorderedCart(memberId, cafeId)) {
            throw new IllegalArgumentException("다른 카페의 장바구니가 이미 존재합니다.");
        }
        cartReader.create(memberId, cafeId);
    }

    public Cart overwriteCart(CartEntity cartEntity, Long cafeId) {
        cartReader.modifyCart(cartEntity.getId(), cafeId);

        return cartEntity.toCart();
    }

    public CartDetail addMenu(Long cartDetailId, Long menuId, int quantity) {
        MenuInfo menuInfo = menuReader.read(menuId);

        if (menuInfo.getStock() <= 0 || menuInfo.getStock() - quantity < 0) {
            throw new IllegalArgumentException("해당 메뉴의 재고가 부족합니다.");
        }

        return cartReader.createDetail(cartDetailId, menuId, quantity);
    }
}

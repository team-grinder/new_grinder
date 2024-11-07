package com.grinder.domain.cart.implement;

import com.grinder.domain.cart.entity.Cart;
import com.grinder.domain.cart.entity.CartDetail;
import com.grinder.domain.cart.model.CartDTO;
import com.grinder.domain.cart.reopository.CartDetailRepository;
import com.grinder.domain.cart.reopository.CartRepository;
import com.grinder.domain.menu.entity.Menu;
import com.grinder.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartManager {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final MenuRepository menuRepository;

    public CartDetail addMenu(Cart cart, Long menuId, int quantity) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다.")
        );

        if (menu.getStock() <= 0 || menu.getStock() - quantity < 0) {
            throw new IllegalArgumentException("해당 메뉴의 재고가 부족합니다.");
        }
        return cartDetailRepository.save(CartDetail.builder()
                .cartId(cart.getId())
                .menuId(menuId)
                .quantity(quantity)
                .build()
        );
    }

    public void createCart(Long memberId, Long cafeId) {
        cartRepository.save(Cart.builder()
                .memberId(memberId)
                .cafeId(cafeId)
                .build()
        );
    }

    public CartDTO overwriteCart(Cart cart, Long cafeId) {
        cartRepository.updateCafeId(cart, cafeId);

        return cart.toCartDTO();
    }
}

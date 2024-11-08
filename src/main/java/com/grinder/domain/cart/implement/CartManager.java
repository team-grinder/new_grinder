package com.grinder.domain.cart.implement;

import com.grinder.domain.cart.entity.CartDetailEntity;
import com.grinder.domain.cart.entity.CartEntity;
import com.grinder.domain.cart.model.CartDTO;
import com.grinder.domain.cart.reopository.CartDetailRepository;
import com.grinder.domain.cart.reopository.CartRepository;
import com.grinder.domain.menu.implement.MenuReader;
import com.grinder.domain.menu.model.MenuInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartManager {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final MenuReader menuReader;
    private final CartReader cartReader;

    public void createCart(Long memberId, Long cafeId) {
        if (cartReader.hasUnorderedCart(memberId, cafeId)) {
            throw new IllegalArgumentException("다른 카페의 장바구니가 이미 존재합니다.");
        }
        cartRepository.save(CartEntity.builder()
                .memberId(memberId)
                .cafeId(cafeId)
                .build()
        );
    }

    public CartDTO overwriteCart(CartEntity cartEntity, Long cafeId) {
        cartRepository.updateCafeId(cartEntity, cafeId);

        return cartEntity.toCartDTO();
    }

    public CartDetailEntity addMenu(CartEntity cartEntity, Long menuId, int quantity) {
        MenuInfo menuInfo = menuReader.read(menuId);

        if (menuInfo.getStock() <= 0 || menuInfo.getStock() - quantity < 0) {
            throw new IllegalArgumentException("해당 메뉴의 재고가 부족합니다.");
        }

        return cartDetailRepository.save(CartDetailEntity.builder()
                .cartId(cartEntity.getId())
                .menuId(menuId)
                .quantity(quantity)
                .build()
        );
    }
}

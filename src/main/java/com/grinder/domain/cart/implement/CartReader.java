package com.grinder.domain.cart.implement;

import com.grinder.domain.cart.entity.CartDetailEntity;
import com.grinder.domain.cart.entity.CartEntity;
import com.grinder.domain.cart.model.Cart;
import com.grinder.domain.cart.model.CartDetail;
import com.grinder.domain.cart.reopository.CartDetailRepository;
import com.grinder.domain.cart.reopository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartReader {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public Cart create(Long memberId, Long cafeId) {
        return cartRepository.save(CartEntity.builder()
                .memberId(memberId)
                .cafeId(cafeId)
                .build()
        ).toCart();
    }

    public CartDetail createDetail(Long cartDetailId, Long menuId, int quantity) {
        return cartDetailRepository.save(CartDetailEntity.builder()
                .cartId(cartDetailId)
                .menuId(menuId)
                .quantity(quantity)
                .build()
        ).toCartDetail();
    }

    public void modifyCart(Long cartId, Long cafeId) {
        cartRepository.updateCafeId(cartId, cafeId);
    }

    public boolean hasUnorderedCart(Long memberId, Long cafeId) {
        return cartRepository.existsByMemberIdAndCafeIdAndIsOrderedIsFalse(memberId, cafeId);
    }

    public CartEntity findCart(Long memberId, Long cafeId) {
        return cartRepository.findByMemberIdAndCafeIdAndIsOrderedIsFalse(memberId, cafeId).orElseThrow(
                () -> new IllegalArgumentException("해당 카페의 장바구니가 존재하지 않습니다.")
        );
    }

    public CartEntity findCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new IllegalArgumentException("해당 장바구니가 존재하지 않습니다.")
        );
    }


}

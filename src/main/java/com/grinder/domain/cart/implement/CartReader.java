package com.grinder.domain.cart.implement;

import com.grinder.domain.cart.entity.CartEntity;
import com.grinder.domain.cart.reopository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartReader {
    private final CartRepository cartRepository;

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

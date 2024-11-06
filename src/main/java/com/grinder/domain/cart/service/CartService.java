package com.grinder.domain.cart.service;

import com.grinder.domain.cart.model.CartVO;
import com.grinder.domain.cart.reader.CartReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final CartReader cartReader;

    @Transactional
    public void createCart(CartVO cartVO) {
        if (cartReader.hasUnorderedCart(cartVO)) {
            throw new IllegalArgumentException("같은 카페의 메뉴만 담을 수 있습니다.");
        }

    }

    public boolean hasUnorderedCart(CartVO cartVO) {
        return cartReader.hasUnorderedCart(cartVO);
    }
}

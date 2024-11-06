package com.grinder.domain.cart.service;

import com.grinder.domain.cart.entity.Cart;
import com.grinder.domain.cart.entity.CartDetail;
import com.grinder.domain.cart.model.CartDTO;
import com.grinder.domain.cart.reader.CartReader;
import com.grinder.domain.member.model.MemberBasicInfo;
import com.grinder.domain.member.reader.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final CartReader cartReader;
    private final MemberReader memberReader;

    @Transactional
    public void createCart(String email, Long cafeId) {
        MemberBasicInfo memberInfo = memberReader.readEmail(email);

        if (cartReader.hasUnorderedCart(memberInfo.getId(), cafeId)) {
            throw new IllegalArgumentException("이미 장바구니에 담긴 상품이 있습니다.");
        }

        cartReader.createCart(memberInfo.getId(), cafeId);
    }

    @Transactional
    public CartDetail addMenu(String email, Long cafeId, Long menuId, int quantity) {
        MemberBasicInfo memberInfo = memberReader.readEmail(email);

        Cart cart = cartReader.findCart(memberInfo.getId(), cafeId);

        return cartReader.addMenu(cart, menuId, quantity);
    }

    @Transactional
    public void overwriteCart(Long cartId, Long cafeId) {
        Cart cart = cartReader.findCart(cartId);

        CartDTO cartDTO = cartReader.overwriteCart(cart, cafeId);
    }

    public boolean hasUnorderedCart(String email, Long cafeId) {
        MemberBasicInfo memberInfo = memberReader.readEmail(email);
        return cartReader.hasUnorderedCart(memberInfo.getId(), cafeId);
    }
}

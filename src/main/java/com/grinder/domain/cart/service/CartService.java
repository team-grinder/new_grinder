package com.grinder.domain.cart.service;

import com.grinder.domain.cart.entity.CartDetailEntity;
import com.grinder.domain.cart.entity.CartEntity;
import com.grinder.domain.cart.implement.CartManager;
import com.grinder.domain.cart.model.CartDTO;
import com.grinder.domain.cart.implement.CartReader;
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
    private final CartManager cartManager;

    @Transactional
    public void createCart(String email, Long cafeId) {
        MemberBasicInfo memberInfo = memberReader.readEmail(email);

        if (cartReader.hasUnorderedCart(memberInfo.getId(), cafeId)) {
            throw new IllegalArgumentException("이미 장바구니에 담긴 상품이 있습니다.");
        }

        cartManager.createCart(memberInfo.getId(), cafeId);
    }

    @Transactional
    public CartDetailEntity addMenu(String email, Long cafeId, Long menuId, int quantity) {
        MemberBasicInfo memberInfo = memberReader.readEmail(email);

        CartEntity cartEntity = cartReader.findCart(memberInfo.getId(), cafeId);

        return cartManager.addMenu(cartEntity, menuId, quantity);
    }

    @Transactional
    public void overwriteCart(Long cartId, Long cafeId) {
        CartEntity cartEntity = cartReader.findCart(cartId);

        CartDTO cartDTO = cartManager.overwriteCart(cartEntity, cafeId);
    }

    public boolean hasUnorderedCart(String email, Long cafeId) {
        MemberBasicInfo memberInfo = memberReader.readEmail(email);
        return cartReader.hasUnorderedCart(memberInfo.getId(), cafeId);
    }
}

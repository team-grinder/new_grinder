package com.grinder.domain.cart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CartServiceTest {
    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("장바구니에 메뉴 추가")
    @Transactional
    public void addMenuToCartTest() {

    }
}
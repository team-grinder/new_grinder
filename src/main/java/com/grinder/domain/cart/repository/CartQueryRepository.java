package com.grinder.domain.cart.repository;

import com.grinder.domain.cart.entity.Cart;
import com.grinder.domain.cart.entity.QCart;
import com.grinder.domain.cart.entity.QCartInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CartQueryRepository {
    private final JPAQueryFactory queryFactory;

    public CartQueryRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Cart> findCartList(Long memberId, Long cafeId) {
        QCart cart = QCart.cart;
        QCartInfo cartInfo = QCartInfo.cartInfo;

        return null;
    }
}

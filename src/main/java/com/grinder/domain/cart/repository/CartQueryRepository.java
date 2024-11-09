package com.grinder.domain.cart.repository;

import com.grinder.domain.cart.entity.CartEntity;
import com.grinder.domain.cart.entity.QCartEntity;
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

    public List<CartEntity> findCartList(Long memberId, Long cafeId) {
        QCartEntity cart = QCartEntity.cartEntity;

        return null;
    }
}

package com.grinder.domain.cart.repository;

import com.grinder.domain.cart.entity.CartEntity;
import com.grinder.domain.cart.entity.QCartEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.grinder.domain.member.entity.QMemberEntity.memberEntity;

@Repository
public class CartQueryRepository {
    private final JPAQueryFactory query;

    public CartQueryRepository(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public List<CartEntity> findCartList(Long memberId, Long cafeId) {
        return null;
    }

    /**
     * TODO: 카트엔 주문 여부가 false 인 특정 카페 메뉴만 담을 수 있어야 하나, 만약 해당 카트가 이미 존재한다면
     * TODO: 삭제 해야 할지? 아니면 수정 해야 할지? DB에 직접적으로 보낸다면 부하가 있을 것으로 예상,
     * TODO: NoSQL을 사용하거나, 수정 삭제 중 비용이 작은 것으로 추후 선택
     */
    public Optional<CartEntity> findCartByEmail(String email, Long cafeId) {
        return Optional.ofNullable(query.from(QCartEntity.cartEntity)
                .select(QCartEntity.cartEntity)
                .leftJoin(memberEntity)
                .on(QCartEntity.cartEntity.memberId.eq(memberEntity.id))
                .where(memberEntity.email.eq(email)
                        .and(QCartEntity.cartEntity.cafeId.eq(cafeId)
                                .and(QCartEntity.cartEntity.isOrdered.isFalse())))
                .fetchFirst());
    }
}

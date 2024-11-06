package com.grinder.domain.cart.reopository;

import com.grinder.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // 회원 아이디와 카페 아이디, ordered가 false인지 확인

    boolean existsByMemberIdAndCafeIdAndOrderedIsFalse(Long memberId, Long cafeId);
}

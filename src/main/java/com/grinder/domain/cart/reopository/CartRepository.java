package com.grinder.domain.cart.reopository;

import com.grinder.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsByMemberIdAndCafeIdAndOrderedIsFalse(Long memberId, Long cafeId);

    Optional<Cart> findByMemberIdAndCafeIdAndOrderedIsFalse(Long memberId, Long cafeId);

    @Modifying
    @Query("update Cart c set c.cafeId = :cafeId where c = :cart and c.isOrdered = false")
    void updateCafeId(Cart cart, Long cafeId);
}

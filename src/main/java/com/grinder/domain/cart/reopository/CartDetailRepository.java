package com.grinder.domain.cart.reopository;

import com.grinder.domain.cart.entity.CartDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetailEntity, Long> {
}

package com.grinder.domain.cart.repository;

import com.grinder.domain.cart.entity.CartDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetailEntity, Long> {
    List<CartDetailEntity> findAllByCartId(Long cartId);
}

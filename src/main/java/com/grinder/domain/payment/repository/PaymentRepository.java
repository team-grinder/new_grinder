package com.grinder.domain.payment.repository;

import com.grinder.domain.payment.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment save(Payment payment);
    Optional<Payment> findByTablingId(Long tablingId);
}

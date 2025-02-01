package com.grinder.domain.payment.service;

import com.grinder.domain.payment.entity.Payment;
import com.grinder.domain.payment.model.PaymentResponse;
import com.grinder.domain.payment.model.PaymentSuccessRequest;
import com.grinder.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentResponse processPayment(PaymentSuccessRequest request) {
        Payment payment = Payment.builder()
                .paymentType(request.getPaymentType())
                .orderId(request.getOrderId())
                .paymentKey(request.getPaymentKey())
                .amount(request.getAmount())
                .tablingId(request.getTablingId())
                .build();
        payment = paymentRepository.save(payment);
        return PaymentResponse.to(payment);
    }
}
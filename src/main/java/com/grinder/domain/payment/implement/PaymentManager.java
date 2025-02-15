package com.grinder.domain.payment.implement;

import com.grinder.common.exception.TablingException;
import com.grinder.domain.payment.entity.Payment;
import com.grinder.domain.payment.model.PaymentResponse;
import com.grinder.domain.payment.model.PaymentSuccessRequest;
import com.grinder.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentManager {
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
    public PaymentResponse getPaymentByTablingId(Long tablingId) {
        return PaymentResponse.to(paymentRepository.findByTablingId(tablingId)
                .orElseThrow(() -> new TablingException("결제 정보를 찾을 수 없습니다.")));
    }
}

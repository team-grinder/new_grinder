package com.grinder.domain.payment.model;

import com.grinder.domain.payment.entity.Payment;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private boolean success;
    private Long paymentId;
    private String paymentType;
    private String orderId;
    private Long amount;
    private String paymentKey;

    public static PaymentResponse to(Payment payment) {
        return PaymentResponse.builder()
                .success(true)
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .paymentType(payment.getPaymentType())
                .paymentKey(payment.getPaymentKey())
                .amount(payment.getAmount())
                .build();
    }
}
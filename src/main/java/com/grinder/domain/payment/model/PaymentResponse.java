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
    private String orderId;

    public static PaymentResponse to(Payment payment) {
        return PaymentResponse.builder()
                .success(true)
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .build();
    }
}
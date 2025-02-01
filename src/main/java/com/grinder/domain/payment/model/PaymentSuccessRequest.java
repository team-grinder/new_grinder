package com.grinder.domain.payment.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSuccessRequest {
    private String paymentType;
    private String orderId;
    private String paymentKey;
    private Long amount;
    private Long tablingId;
}
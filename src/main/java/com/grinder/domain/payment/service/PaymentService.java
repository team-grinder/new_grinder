package com.grinder.domain.payment.service;

import com.grinder.domain.payment.implement.PaymentManager;
import com.grinder.domain.payment.model.PaymentResponse;
import com.grinder.domain.payment.model.PaymentSuccessRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentManager paymentManger;
    public PaymentResponse processPayment(PaymentSuccessRequest request){
        return paymentManger.processPayment(request);
    }

    public PaymentResponse getPaymentByTabling(Long tablingId){
        return paymentManger.getPaymentByTablingId(tablingId);
    }
}
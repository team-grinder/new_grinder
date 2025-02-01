package com.grinder.web.main.payment;

import com.grinder.domain.payment.model.PaymentResponse;
import com.grinder.domain.payment.model.PaymentSuccessRequest;
import com.grinder.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/success")
    public ResponseEntity<PaymentResponse> handlePaymentSuccess(@RequestBody PaymentSuccessRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.ok(response);
    }
}

package com.grinder.domain.tabling.model;

import com.grinder.domain.cafe.model.Cafe;
import com.grinder.domain.payment.model.PaymentResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tabling {
    private Long id;
    private Long cafeId;
    private Long memberId;
    private String paymentId;
    private Long paymentAmount;
    private String cafeName;
    private String address;
    private LocalDate date;
    private LocalTime reserveTime;
    private Integer numberOfGuests;
    private TablingStatus status;

    public static Tabling from(Tabling tabling, PaymentResponse payment, Cafe cafe) {
        return Tabling.builder()
                .id(tabling.getId())
                .cafeId(cafe.getId())
                .cafeName(cafe.getName())
                .address(cafe.getAddress())
                .numberOfGuests(tabling.getNumberOfGuests())
                .paymentId(payment.getPaymentKey())
                .paymentAmount(payment.getAmount())
                .status(tabling.getStatus())
                .reserveTime(tabling.getReserveTime())
                .date(tabling.getDate())
                .build();
    }
}

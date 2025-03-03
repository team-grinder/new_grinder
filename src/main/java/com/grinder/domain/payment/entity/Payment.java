package com.grinder.domain.payment.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
public class Payment {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long tablingId;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private String paymentKey;

    @Column(nullable = false)
    private Long amount;
}

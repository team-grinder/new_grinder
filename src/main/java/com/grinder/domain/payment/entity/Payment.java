package com.grinder.domain.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

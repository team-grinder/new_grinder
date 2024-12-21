package com.grinder.domain.tabling.entity;


import com.grinder.common.annotation.Name;
import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.tabling.model.Tabling;
import com.grinder.domain.tabling.model.TablingStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class TablingEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;

    private LocalDate tablingDate;

    private LocalTime timeSlot;

    private int currentNumber;

    private Long cartId;

    private int maxNumber;

    @Enumerated(EnumType.STRING)
    private TablingStatus status;

    private String reason;

    @Name(name = "카페 정보 연관 관계")
    private Long cafeId;

    public Tabling toTabling() {
        return Tabling.builder()
                .id(id)
                .memberId(memberId)
                .cafeId(cafeId)
                .reservationDate(tablingDate)
                .timeSlot(timeSlot)
                .numberOfPeople(currentNumber)
                .status(status)
                .cartId(cartId)
                .build();
    }
}

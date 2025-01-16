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
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class TablingEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long cafeId;
    private Long memberId;

    private LocalDate date;
    private LocalTime reserveTime;
    private Integer numberOfGuests;

    @Enumerated(EnumType.STRING)
    private TablingStatus status;

    @Version
    private Long version;

    public Tabling toTabling() {
        return Tabling.builder()
                .id(id)
                .cafeId(cafeId)
                .memberId(memberId)
                .date(date)
                .reserveTime(reserveTime)
                .numberOfGuests(numberOfGuests)
                .status(status)
                .build();
    }

    public void updateStatus(TablingStatus status) {
        this.status = status;
    }
}

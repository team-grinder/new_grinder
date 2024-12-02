package com.grinder.domain.tabling.entity;

import com.grinder.common.entity.BaseDateEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation_time_slot",
        uniqueConstraints = @UniqueConstraint(columnNames = {"cafe_id", "reservation_date", "time_slot"}))
public class TablingTimeSlotEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "cafe_id")
    private Long cafeId;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "time_slot")
    private LocalTime timeSlot;

    private Integer maxReservations;

    private Integer currentReservations;

    @Version
    private Long version;

}

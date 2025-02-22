package com.grinder.domain.cafe.entity;

import com.grinder.common.entity.BaseDateEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CafeBusinessHourEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long cafeId;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Integer maxTimePerReservation;

    @Column(nullable = false)
    private Integer maxGuestsPerTime;

    @Column(nullable = false)
    private Boolean isActive;

    @ElementCollection
    @CollectionTable(name = "cafe_blocked_hours")
    private List<LocalTime> blockedTimes;

    @Builder
    public CafeBusinessHourEntity(Long cafeId, LocalTime startTime, LocalTime endTime,
                                  Integer maxTimePerReservation, Integer maxGuestsPerTime, List<LocalTime> blockedTimes) {
        this.cafeId = cafeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxTimePerReservation = maxTimePerReservation;
        this.maxGuestsPerTime = maxGuestsPerTime;
        this.blockedTimes = blockedTimes;
        this.isActive = true;
    }

    public void updateBusinessHours(LocalTime startTime, LocalTime endTime,
                                     Integer maxTimePerReservation,Integer maxGuestsPerTime, List<LocalTime> blockedTimes) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxTimePerReservation = maxTimePerReservation;
        this.maxGuestsPerTime = maxGuestsPerTime;
        this.blockedTimes = blockedTimes;
    }
}

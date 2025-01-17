package com.grinder.domain.tabling.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.common.exception.TablingException;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TablingTimeSlotEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long cafeId;
    private LocalDate date;
    private LocalTime reserveTime;
    private Integer maxGuests;
    private Integer currentGuests;
    private Boolean isAvailable;

    @Version
    private Long version;

    @Builder
    public TablingTimeSlotEntity(Long cafeId, LocalDate date,
                              LocalTime reserveTime, Integer maxGuests) {
        this.cafeId = cafeId;
        this.date = date;
        this.reserveTime = reserveTime;
        this.maxGuests = maxGuests;
        this.currentGuests = 0;
        this.isAvailable = true;
    }
    public boolean canReserve(int numberOfGuests) {
        return isAvailable &&
                (currentGuests + numberOfGuests <= maxGuests);
    }

    public void addGuests(int numberOfGuests) {
        if (!canReserve(numberOfGuests)) {
            throw new TablingException(
                    String.format("예약 가능 인원을 초과했습니다. (현재 가능 인원: %d)",
                            maxGuests - currentGuests)
            );
        }
        this.currentGuests += numberOfGuests;
    }

    public void removeGuests(int numberOfGuests) {
        this.currentGuests -= numberOfGuests;
        if (this.currentGuests < 0) {
            this.currentGuests = 0;
        }
    }

}

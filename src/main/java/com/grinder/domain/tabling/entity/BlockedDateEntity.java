package com.grinder.domain.tabling.entity;


import com.grinder.common.entity.BaseDateEntity;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BlockedDateEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long cafeId;
    private LocalDate date;
    private String reason;

    @Builder
    public BlockedDateEntity(Long cafeId, LocalDate date, String reason) {
        this.cafeId = cafeId;
        this.date = date;
        this.reason = reason;
    }
}


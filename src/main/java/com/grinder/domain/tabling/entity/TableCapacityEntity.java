package com.grinder.domain.tabling.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.tabling.model.TableCapacity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class TableCapacityEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long cafeId;

    private Integer minCapacity;

    private Integer maxCapacity;

    @Version
    private Long version;

    public TableCapacity toTableCapacity() {
        return TableCapacity.builder()
                .id(id)
                .cafeId(cafeId)
                .minCapacity(minCapacity)
                .maxCapacity(maxCapacity)
                .build();
    }
}

package com.grinder.domain.cafe.entity;


import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.cafe.model.Cafe;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class CafeEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;

    private String description;

    private String tel;

    private String businessNumber;

    private Long imageId;

    @Builder
    public CafeEntity(String name, String address, String description, String tel, String businessNumber) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.tel = tel;
        this.businessNumber = businessNumber;
    }

    public Cafe toCafe() {
        return Cafe.builder()
                .id(id)
                .name(name)
                .address(address)
                .description(description)
                .tel(tel)
                .build();
    }
}

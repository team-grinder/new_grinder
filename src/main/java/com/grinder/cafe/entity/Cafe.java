package com.grinder.cafe.entity;


import com.grinder.common.entity.BaseDateEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Cafe extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;

    private String tel;

}

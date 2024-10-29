package com.grinder.domain.menu.entity;


import com.grinder.common.annotation.Name;
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
public class Option extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String price;

    private int stock;

    @Name(name = "메뉴 정보 연관 관계", description = "메뉴 선택 시 나오는 소메뉴")
    private Long MenuId;
}

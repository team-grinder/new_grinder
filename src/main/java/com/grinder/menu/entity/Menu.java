package com.grinder.menu.entity;


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
public class Menu extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private String price;

    @Name(name = "화면에 보일 이미지 순서")
    private int sequence;

    @Name(name = "이미지 정보 연관 관계")
    private Long imageId;

    @Name(name = "카페 정보 연관 관계")
    private Long cafeId;
}

package com.grinder.domain.menu.entity;


import com.grinder.common.annotation.Name;
import com.grinder.common.entity.BaseDateEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Menu extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private String price;
    
    private int stock;
    
    @Name(name = "시즌 메뉴 여부")
    private boolean seasonYn;

    @Name(name = "화면에 보일 이미지 순서")
    private int sequence;

    @Name(name = "이미지 정보 연관 관계")
    private Long imageId;

    @Name(name = "카페 정보 연관 관계")
    private Long cafeId;
}

package com.grinder.cart.entity;


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
public class Cart extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Name(name = "회원 정보 연관 관계")
    private Long memberId;

    @Name(name = "메뉴 정보 연관 관계")
    private Long MenuId;

    @Name(name = "카페 정보 연관 관계")
    private Long CafeId;
}

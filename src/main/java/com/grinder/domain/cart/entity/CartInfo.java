package com.grinder.domain.cart.entity;

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
public class CartInfo {
    @Id
    @GeneratedValue
    private Long id;

    @Name(name = "카트 연관 관계")
    private Long cartId;

    @Name(name = "메뉴 리스트 연관 관계")
    private Long menuId;
}

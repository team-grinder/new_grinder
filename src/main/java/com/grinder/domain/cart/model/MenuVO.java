package com.grinder.domain.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO {
    private Long menuId;

    private Long cafeId;

    private int quantity;
}

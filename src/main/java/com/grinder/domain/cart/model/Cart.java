package com.grinder.domain.cart.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Cart {
    private Long id;

    private Long memberId;

    private Long cafeId;
}

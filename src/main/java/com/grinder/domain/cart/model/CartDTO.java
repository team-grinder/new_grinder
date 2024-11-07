package com.grinder.domain.cart.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartDTO {
    private Long id;

    private Long memberId;

    private Long cafeId;
}

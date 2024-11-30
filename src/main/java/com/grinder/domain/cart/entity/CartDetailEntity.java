package com.grinder.domain.cart.entity;

import com.grinder.common.annotation.Name;
import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.cart.converter.OptionListConverter;
import com.grinder.domain.cart.model.CartDetail;
import com.grinder.domain.menu.model.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class CartDetailEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Name(name = "카트 연관 관계")
    private Long cartId;

    @Name(name = "메뉴 리스트 연관 관계")
    private Long menuId;

    @Name(name = "옵션 리스트")
    @Convert(converter = OptionListConverter.class)
    private List<Option> options;

    @Name(name = "수량")
    private int quantity;

    public CartDetail toCartDetail() {
        return CartDetail.builder()
                .id(id)
                .cartId(cartId)
                .menuId(menuId)
                .quantity(quantity)
                .build();
    }
}

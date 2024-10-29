package com.grinder.domain.cart.entity;


import com.grinder.common.annotation.Name;
import com.grinder.common.entity.BaseDateEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"memberId", "cafeId"})
})
public class Cart extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Name(name = "회원 정보 연관 관계")
    private Long memberId;

    @Name(name = "카페 정보 연관 관계")
    private Long cafeId;

}

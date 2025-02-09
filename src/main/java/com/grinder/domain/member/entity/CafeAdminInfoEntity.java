package com.grinder.domain.member.entity;

import com.grinder.common.entity.BaseDateEntity;
import com.grinder.domain.member.model.CafeAdminInfoRegister;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cafeAdminId", callSuper = false)
@Builder
public class CafeAdminInfoEntity extends BaseDateEntity {
    @Id
    private String cafeAdminId;

    @Column(nullable = false)
    private String businessNumber;//사업자 번호

    @Column(nullable = false)
    private String businessName;//사업자명
    private String businessAddress;//사업자 주소
    private String businessContact;//사업자 번호

    public void updateCafeAdminInfo(CafeAdminInfoRegister register){
        this.businessAddress=register.getBusinessAddress();
        this.businessContact=register.getBusinessContact();
        this.businessName=register.getBusinessName();
        this.businessNumber=register.getBusinessNumber();
    }
}

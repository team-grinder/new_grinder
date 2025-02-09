package com.grinder.domain.member.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CafeAdminInfoRegister {
    @NotBlank(message = "사업자 등록 번호는 필수입니다")
    @Pattern(regexp = "^\\d{10}$", message = "올바른 사업자 등록 번호 형식이 아닙니다")
    private String businessNumber;

    @NotBlank(message = "상호명은 필수입니다")
    private String businessName;

    @NotBlank(message = "사업장 주소는 필수입니다")
    private String businessAddress;

    @NotBlank(message = "연락처는 필수입니다")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다")
    private String businessContact;
}

package com.grinder.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS("200", "성공"),
    NO_CONTENT("204", "데이터가 없습니다."),
    BAD_REQUEST("400", "잘못된 요청입니다."),
    UNAUTHORIZED("401", "인증이 필요합니다."),
    FORBIDDEN("403", "권한이 없습니다."),
    NOT_FOUND("404", "찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR("500", "서버 내부 오류");

    private final String code;
    private final String message;

    public static ResultEnum getByStatus(String status) {
        for (ResultEnum resultEnum : values()) {
            if (resultEnum.getCode().equals(status)) {
                return resultEnum;
            }
        }
        throw new IllegalArgumentException("No matching ResultEnum for status: " + status);
    }
}

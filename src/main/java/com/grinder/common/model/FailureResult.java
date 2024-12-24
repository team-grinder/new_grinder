package com.grinder.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class FailureResult<T> {
    private String code;
    private String message;
    private T data;

    public static <T> FailureResult<T> of(ReslutEnum resultEnum, T data) {
        return new FailureResult<>(resultEnum.getCode(), resultEnum.getMessage(), data);
    }

    public static FailureResult<ErrorResult> from(AuthResultEnum authErrorCode) {
        ErrorResult errorResult = ErrorResult.from(authErrorCode);
        return new FailureResult<>(
                authErrorCode.getStatus(),  // HTTP 상태 코드
                ReslutEnum.valueOf(authErrorCode.getStatus()).getMessage(),  // 상태 메시지
                errorResult
        );
    }
}
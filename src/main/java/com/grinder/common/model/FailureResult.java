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

    public static FailureResult<ErrorResult> from(AuthResultEnum authResultEnum) {
        ReslutEnum resultEnum = ReslutEnum.getByStatus(authResultEnum.getStatus());
        ErrorResult errorResult = new ErrorResult(authResultEnum.getCode(), authResultEnum.getMessage());
        return new FailureResult<>(resultEnum.getCode(), resultEnum.getMessage(), errorResult);
    }
}
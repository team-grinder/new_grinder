package com.grinder.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResult<T> {
    private String code;
    private String message;
    private T data;

    public static <T> SuccessResult<T> of(ResultEnum resultEnum, T data) {
        return new SuccessResult<>(resultEnum.getCode(), resultEnum.getMessage(), data);
    }
    public static <Void> SuccessResult<Void> of(ResultEnum resultEnum) {
        return new SuccessResult<>(resultEnum.getCode(), resultEnum.getMessage(), null);
    }
}

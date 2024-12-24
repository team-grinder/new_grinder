package com.grinder.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResult {
    private String errorCode;
    private String errorMessage;


    public static ErrorResult from(AuthResultEnum errorCode) {
        return new ErrorResult(errorCode.getCode(), errorCode.getMessage());
    }
}

package com.example.delivery.global.exception;

import org.springframework.http.HttpStatus;

public record ErrorCode(
        HttpStatus httpStatus,
        ReasonCode reasonCode
) {

    public static ErrorCode of(HttpStatus httpStatus, ReasonCode reasonCode) {
        return new ErrorCode(httpStatus, reasonCode);
    }

    public String getErrorCode() {
        return String.valueOf(httpStatus.value() + reasonCode.getCode());
    }
}

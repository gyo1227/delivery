package com.example.delivery.domain.user.exception.enums;

import com.example.delivery.global.exception.ErrorCode;
import com.example.delivery.global.exception.ApiError;
import com.example.delivery.global.exception.ReasonCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ApiError {

    ;

    private final HttpStatus httpStatus;
    private final ReasonCode reasonCode;
    private final String message;

    @Override
    public ErrorCode errorCode() {
        return ErrorCode.of(httpStatus, reasonCode);
    }

    @Override
    public String message() {
        return message;
    }
}

package com.example.delivery.domain.user.exception.enums;

import com.example.delivery.global.exception.ErrorCode;
import com.example.delivery.global.exception.ApiError;
import com.example.delivery.global.exception.ReasonCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ApiError {

    ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, ReasonCode.RESOURCE_ALREADY_EXISTS, "이미 등록된 이메일입니다."),
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

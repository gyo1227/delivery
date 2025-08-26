package com.example.delivery.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReasonCode {

    // 405
    REQUEST_METHOD_NOT_SUPPORTED(1),

    // 409
    RESOURCE_ALREADY_EXISTS(1),

    // 422
    REQUIRED_PARAMETER_VALIDATION_ERROR(1),

    // 500
    UNEXPECTED_ERROR(0)
    ;

    private final int code;
}

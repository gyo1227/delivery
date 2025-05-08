package kr.co.delivery.common.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReasonCode {

    // 400
    REQUIRED_PARAMETER_VALIDATION_ERROR(0),

    // 409
    RESOURCE_ALREADY_EXISTS(0),

    // 500
    UNEXPECTED_ERROR(0)
    ;

    private final int code;
}

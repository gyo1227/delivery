package com.example.delivery.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReasonCode {

    // 500
    UNEXPECTED_ERROR(0)
    ;

    private final int code;
}

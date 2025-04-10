package kr.co.delivery.common.exception.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReasonCode {

    // 500 INTERNAL_SERVER_ERROR,
    UNEXPECTED_ERROR(0)
    ;

    private final int code;
}

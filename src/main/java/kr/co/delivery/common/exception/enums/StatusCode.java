package kr.co.delivery.common.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

    SUCCESS(200),

    BAD_REQUEST(400),

    INTERNAL_SERVER_ERROR(500),
    ;

    private final int code;
}

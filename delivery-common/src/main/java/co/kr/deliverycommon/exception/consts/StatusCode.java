package co.kr.deliverycommon.exception.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Http 상태 코드
 */
@Getter
@RequiredArgsConstructor
public enum StatusCode {

    SUCCESS(200),
    CREATED(201),

    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),

    INTERNAL_SERVER_ERROR(500),
    ;

    private final int code;
}

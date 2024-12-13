package kr.co.delivery.common.exception.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 에러 발생 이유 코드
 */
@Getter
@RequiredArgsConstructor
public enum DetailCode {

    // 400 BAD_REQUEST
    INVALID_REQUEST_SYNTAX(0),
    MISSING_REQUIRED_PARAMETER(1),
    MALFORMED_PARAMETER(2),
    MALFORMED_REQUEST_BODY(3),

    // 401 UNAUTHORIZED
    INVALID_AUTHENTICATION_CREDENTIALS(0),
    MISSING_TOKEN(1),
    INVALID_TOKEN(2),
    EXPIRED_TOKEN(3),
    INSUFFICIENT_AUTHORITY(4),

    // 403 FORBIDDEN
    IP_ADDRESS_BLOCKED(0),

    // 404 NOT_FOUND
    REQUESTED_RESOURCE_NOT_FOUND(0),
    INVALID_URL_OR_ENDPOINT(1),

    // 409 CONFLICT
    RESOURCE_ALREADY_EXISTS(0),

    // 422 UNPROCESSABLE_CONTENT
    REQUIRED_PARAMETERS_MISSING_IN_REQUEST_BODY(0),

    // 500 INTERNAL_SERVER_ERROR,
    UNEXPECTED_ERROR(0)
    ;

    private final int code;

}

package org.delivery.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User 에러는 1000번대 에러 코드 사용
 */
@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeIfs {

    VALIDATED_ERROR(400, 1401, "검증 에러입니다."),
    DUPLICATED_ERROR(400, 1402, "중복 에러입니다."),

    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;

}

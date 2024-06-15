package org.delivery.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * StoreUser 에러는 3000번대 에러 코드 사용
 */

@Getter
@AllArgsConstructor
public enum StoreUserErrorCode implements ErrorCodeIfs {

    VALIDATED_ERROR(400, 3401, "검증 에러입니다."),
    DUPLICATED_ERROR(400, 3402, "중복 에러입니다."),
    USER_NOT_FOUND(400, 3403, "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCHED(400, 3404, "비밀번호가 맞지 않습니다.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}

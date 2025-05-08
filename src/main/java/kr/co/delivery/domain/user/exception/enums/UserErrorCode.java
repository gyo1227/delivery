package kr.co.delivery.domain.user.exception.enums;

import kr.co.delivery.common.exception.Error;
import kr.co.delivery.common.exception.ErrorCode;
import kr.co.delivery.common.exception.enums.ReasonCode;
import kr.co.delivery.common.exception.enums.StatusCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements Error {

    // 409 CONFLICT
    ALREADY_EXIST_EMAIL(StatusCode.CONFLICT, ReasonCode.RESOURCE_ALREADY_EXISTS, "이미 가입된 이메일입니다."),
    ALREADY_EXIST_PHONE(StatusCode.CONFLICT, ReasonCode.RESOURCE_ALREADY_EXISTS, "이미 가입된 전화번호입니다."),
    ;

    private final StatusCode statusCode;
    private final ReasonCode reasonCode;
    private final String message;


    @Override
    public ErrorCode errorCode() {
        return ErrorCode.of(statusCode, reasonCode);
    }

    @Override
    public String errorMessage() {
        return message;
    }
}

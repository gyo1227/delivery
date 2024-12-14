package kr.co.delivery.api.common.exception.enums;

import kr.co.delivery.common.exception.consts.BaseErrorCode;
import kr.co.delivery.common.exception.consts.DetailCode;
import kr.co.delivery.common.exception.consts.ErrorCode;
import kr.co.delivery.common.exception.consts.StatusCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    // 409 CONFLICT
    ALREADY_EXIST_EMAIL(StatusCode.CONFLICT, DetailCode.RESOURCE_ALREADY_EXISTS, "이미 존재하는 이메일입니다")
    ;

    private final StatusCode statusCode;
    private final DetailCode detailCode;
    private final String message;

    @Override
    public ErrorCode errorCode() {
        return ErrorCode.of(statusCode, detailCode);
    }

    @Override
    public String errorMessage() {
        return message;
    }
}

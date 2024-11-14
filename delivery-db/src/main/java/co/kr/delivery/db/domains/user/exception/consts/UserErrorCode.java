package co.kr.delivery.db.domains.user.exception.consts;

import co.kr.delivery.common.exception.consts.BaseErrorCode;
import co.kr.delivery.common.exception.consts.DetailCode;
import co.kr.delivery.common.exception.consts.ErrorCode;
import co.kr.delivery.common.exception.consts.StatusCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

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

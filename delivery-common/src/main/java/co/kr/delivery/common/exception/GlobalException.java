package co.kr.delivery.common.exception;

import co.kr.delivery.common.exception.consts.BaseErrorCode;
import co.kr.delivery.common.exception.consts.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final BaseErrorCode baseErrorCode;

    public GlobalException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.errorCode().detailCode().name());
        this.baseErrorCode = baseErrorCode;
    }

    public ErrorCode errorCode() {
        return baseErrorCode.errorCode();
    }
}

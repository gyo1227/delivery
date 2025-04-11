package kr.co.delivery.common.exception;

import kr.co.delivery.common.exception.consts.BaseErrorCode;
import kr.co.delivery.common.exception.consts.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalErrorException extends RuntimeException {

    private final BaseErrorCode baseErrorCode;

    public GlobalErrorException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.errorCode().reasonCode().name());
        this.baseErrorCode = baseErrorCode;
    }

    public ErrorCode errorCode() {
        return baseErrorCode.errorCode();
    }
}

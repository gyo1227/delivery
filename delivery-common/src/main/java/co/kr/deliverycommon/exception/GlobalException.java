package co.kr.deliverycommon.exception;

import co.kr.deliverycommon.exception.consts.BaseErrorCode;
import co.kr.deliverycommon.exception.consts.ErrorCode;
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

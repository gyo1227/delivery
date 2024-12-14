package kr.co.delivery.api.common.exception;

import kr.co.delivery.api.common.exception.enums.UserErrorCode;
import kr.co.delivery.common.exception.GlobalErrorException;
import kr.co.delivery.common.exception.consts.ErrorCode;

public class UserErrorException extends GlobalErrorException {

    private final UserErrorCode userErrorCode;

    public UserErrorException(UserErrorCode userErrorCode) {
        super(userErrorCode);
        this.userErrorCode = userErrorCode;
    }

    public ErrorCode errorCode() {
        return userErrorCode.errorCode();
    }
}

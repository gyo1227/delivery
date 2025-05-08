package kr.co.delivery.domain.user.exception;

import kr.co.delivery.common.exception.CustomException;
import kr.co.delivery.common.exception.ErrorCode;
import kr.co.delivery.domain.user.exception.enums.UserErrorCode;

public class UserException extends CustomException {

    private final UserErrorCode userErrorCode;

    public UserException(UserErrorCode userErrorCode) {
        super(userErrorCode);
        this.userErrorCode = userErrorCode;
    }

    public ErrorCode getUserErrorCode() {
        return userErrorCode.errorCode();
    }
}

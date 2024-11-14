package co.kr.delivery.db.domains.user.exception;

import co.kr.delivery.common.exception.GlobalException;
import co.kr.delivery.common.exception.consts.ErrorCode;
import co.kr.delivery.db.domains.user.exception.consts.UserErrorCode;

public class UserException extends GlobalException {

    private final UserErrorCode userErrorCode;

    public UserException(UserErrorCode userErrorCode) {
        super(userErrorCode);
        this.userErrorCode = userErrorCode;
    }

    public ErrorCode errorCode() {
        return userErrorCode.errorCode();
    }

}

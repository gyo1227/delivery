package org.delivery.common.exception;

import lombok.Getter;
import org.delivery.common.error.ErrorCode;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorMessage;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }

    public ApiException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ApiException(ErrorCode errorCode, Throwable t) {
        super(t);
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}

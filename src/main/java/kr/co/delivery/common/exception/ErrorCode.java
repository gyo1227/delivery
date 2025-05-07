package kr.co.delivery.common.exception;

import kr.co.delivery.common.exception.enums.ReasonCode;
import kr.co.delivery.common.exception.enums.StatusCode;

public record ErrorCode(
        StatusCode statusCode,
        ReasonCode reasonCode
) {
    public static ErrorCode of(StatusCode statusCode, ReasonCode reasonCode) {
        return new ErrorCode(statusCode, reasonCode);
    }

    public String getErrorCode() {
        return generateCode();
    }

    private String generateCode() {
        return String.valueOf(statusCode.getCode() * 10 + reasonCode.getCode());
    }
}

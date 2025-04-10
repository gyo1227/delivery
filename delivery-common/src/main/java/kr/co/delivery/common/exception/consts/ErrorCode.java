package kr.co.delivery.common.exception.consts;

public record ErrorCode(
        StatusCode statusCode,
        ReasonCode reasonCode
) {
    public static ErrorCode of(StatusCode statusCode, ReasonCode reasonCode) {
        return new ErrorCode(statusCode, reasonCode);
    }
}

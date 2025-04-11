package kr.co.delivery.common.exception.consts;

public record ErrorCode(
        StatusCode statusCode,
        ReasonCode reasonCode
) {
    public ErrorCode {
        if (statusCode == null || reasonCode == null) {
            throw new IllegalArgumentException("StatusCode and ReasonCode cannot be null.");
        }
        
        if (!isValidCode(statusCode.getCode(), reasonCode.getCode())) {
            throw new IllegalArgumentException("Invalid StatusCode or ReasonCode.");
        }
    }

    public static ErrorCode of(StatusCode statusCode, ReasonCode reasonCode) {
        return new ErrorCode(statusCode, reasonCode);
    }

    public String getCode() {
        return generateCode();
    }

    private String generateCode() {
        return String.valueOf(statusCode.getCode() * 10 + reasonCode.getCode());
    }

    private boolean isValidCode(int statusCode, int reasonCode) {
        boolean isValidStatusCode = (statusCode >= 100 && statusCode <= 999);

        boolean isValidReasonCode = (reasonCode >= 0 && reasonCode <= 9);

        return isValidStatusCode && isValidReasonCode;
    }
}

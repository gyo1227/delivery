package co.kr.deliverycommon.exception.consts;

import java.util.Objects;
import java.util.stream.Stream;

public record ErrorCode(
        StatusCode statusCode,
        DetailCode detailCode
) {

    public ErrorCode {
        Objects.requireNonNull(statusCode, "statusCode must not be null");
        Objects.requireNonNull(detailCode, "detailCode must not be null");

        if(isValidCode(statusCode.getCode(), detailCode.getCode())) {
            throw new IllegalArgumentException("Invalid error code digits");
        }
    }

    public static ErrorCode of(StatusCode statusCode, DetailCode detailCode) {
        return new ErrorCode(statusCode, detailCode);
    }

    public String getCode() {
        return generateCode();
    }

    public String getDetail() {
        return detailCode.name();
    }

    private String generateCode() {
        return String.valueOf(statusCode.getCode() * 10 + detailCode.getCode());
    }

    private boolean isValidCode(int statusCode, int detailCode) {
        return isValidDigits(statusCode, 3) && isValidDigits(detailCode, 1);
    }

    private boolean isValidDigits(int code, long expectedDigit) {
        return calcDigit(code) == expectedDigit;
    }

    private long calcDigit(int code) {
        if(code == 0) return 1;
        return Stream.iterate(code, n -> n > 0, n -> n / 10).count();
    }
}

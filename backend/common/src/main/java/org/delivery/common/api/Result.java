package org.delivery.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.common.error.ErrorCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer code;
    private String message;

    public static Result Ok() {
        return Result.builder()
                .code(ErrorCode.OK.getErrorCode())
                .message(ErrorCode.OK.getMessage())
                .build();
    }

    public static Result Error(ErrorCode errorCode) {
        return Result.builder()
                .code(errorCode.getErrorCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static Result Error(ErrorCode errorCode, Throwable t) {
        return Result.builder()
                .code(errorCode.getErrorCode())
                .message(t.getLocalizedMessage())
                .build();
    }

    public static Result Error(ErrorCode errorCode, String message) {
        return Result.builder()
                .code(errorCode.getErrorCode())
                .message(message)
                .build();
    }
}

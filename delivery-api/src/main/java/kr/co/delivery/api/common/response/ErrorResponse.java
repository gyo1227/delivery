package kr.co.delivery.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        @NotNull String code,
        @NotNull String message,
        @Nullable Object fieldErrors
) {
    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message, null);
    }

    public static ErrorResponse error(String code, String message, Object fieldErrors) {
        return new ErrorResponse(code, message, fieldErrors);
    }
}

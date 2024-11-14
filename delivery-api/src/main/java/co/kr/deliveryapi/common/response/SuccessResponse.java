package co.kr.deliveryapi.common.response;

import org.jetbrains.annotations.NotNull;

public record SuccessResponse<T>(
        @NotNull T data
) {

    private static final String code = "2000";

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>(data);
    }
}

package kr.co.delivery.api.common.response;

public record SuccessResponse<T>(
        T data
) {
    private static final String code = "2000";

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>(data);
    }
}

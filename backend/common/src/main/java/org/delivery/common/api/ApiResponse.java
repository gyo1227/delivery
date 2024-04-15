package org.delivery.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.common.error.ErrorCode;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private Result result;

    @Valid
    private T body;

    public static <T> ApiResponse<T> Ok(T data) {
        var apiResponse = new ApiResponse<T>();
        apiResponse.result = Result.Ok();
        apiResponse.body = data;
        return apiResponse;
    }

    public static ApiResponse<Object> Error(Result result) {
        var apiResponse = new ApiResponse<Object>();
        apiResponse.result = result;
        return apiResponse;
    }

    public static ApiResponse<Object> Error(ErrorCode errorCode) {
        var apiResponse = new ApiResponse<Object>();
        apiResponse.result = Result.Error(errorCode);
        return apiResponse;
    }

    public static ApiResponse<Object> Error(ErrorCode errorCode, Throwable t) {
        var apiResponse = new ApiResponse<Object>();
        apiResponse.result = Result.Error(errorCode, t);
        return apiResponse;
    }

    public static ApiResponse<Object> Error(ErrorCode errorCode, String message) {
        var apiResponse = new ApiResponse<Object>();
        apiResponse.result = Result.Error(errorCode, message);
        return apiResponse;
    }
}

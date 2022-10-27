
package com.nds.ssagdaglo.common;

import lombok.Getter;

// 클라이언트에 보내줄 응답 상태와 오류 상태 정의
@Getter
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "sucess";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    // 성공 + 데이터 반환
    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }

    // 성공
    public static ApiResponse<?> createSuccessWithNoContent() {
        return new ApiResponse<>(SUCCESS_STATUS, null, null);
    }

    // 실패
    public static ApiResponse<?> createError(String message) {
        return new ApiResponse<>(ERROR_STATUS, null, message);
    }

    private ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

}

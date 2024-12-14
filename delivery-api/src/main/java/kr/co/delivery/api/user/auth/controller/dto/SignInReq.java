package kr.co.delivery.api.user.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInReq(
        @NotBlank(message = "이메일를 입력해주세요")
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요")
        String password
) {
}

package kr.co.delivery.domain.user.controller;

import jakarta.validation.Valid;
import kr.co.delivery.common.response.ApiResponse;
import kr.co.delivery.domain.user.dto.UserSignupRequest;
import kr.co.delivery.domain.user.dto.UserSignupResponse;
import kr.co.delivery.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserSignupResponse>> signup(@Valid @RequestBody UserSignupRequest request) {
        UserSignupResponse response = userService.signup(request);
        return ResponseEntity.ok(ApiResponse.success("회원가입이 완료되었습니다.", response));
    }
}

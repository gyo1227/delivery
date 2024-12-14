package kr.co.delivery.api.user.auth.controller;

import kr.co.delivery.api.common.response.SuccessResponse;
import kr.co.delivery.api.user.auth.controller.dto.SignUpReq;
import kr.co.delivery.api.user.auth.controller.dto.UserAuthResponse;
import kr.co.delivery.api.user.auth.service.UserService;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/auth/user")
@RestController
public class AuthController {

    private final UserService userService;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Validated SignUpReq request) {
        UserEntity userEntity = userService.signUp(request);
        UserAuthResponse response = UserAuthResponse.fromEntity(userEntity);
        return ResponseEntity.ok(SuccessResponse.of(response));
    }

}

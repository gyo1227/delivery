package kr.co.delivery.api.user.auth.controller;

import kr.co.delivery.api.common.response.SuccessResponse;
import kr.co.delivery.api.common.security.jwt.Jwts;
import kr.co.delivery.api.common.util.CookieUtil;
import kr.co.delivery.api.user.auth.controller.dto.SignInReq;
import kr.co.delivery.api.user.auth.controller.dto.SignUpReq;
import kr.co.delivery.api.user.auth.controller.dto.UserAuthResponse;
import kr.co.delivery.api.user.auth.service.UserService;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RequiredArgsConstructor
@RequestMapping("/v1/auth/user")
@RestController
public class AuthController {

    private final UserService userService;
    private final CookieUtil cookieUtil;

    @PreAuthorize("isAnonymous()")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Validated SignUpReq request) {
        UserEntity userEntity = userService.signUp(request);
        UserAuthResponse response = UserAuthResponse.fromEntity(userEntity);
        return ResponseEntity.ok(SuccessResponse.of(response));
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Validated SignInReq request) {
        Pair<Long, Jwts> userInfo = userService.signIn(request);
        return createAuthResponse(userInfo);
    }

    private ResponseEntity<?> createAuthResponse(Pair<Long, Jwts> userInfo) {
        ResponseCookie cookie = cookieUtil.createCookie("refreshToken", userInfo.getValue().refreshToken(), Duration.ofDays(7).toSeconds());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.AUTHORIZATION, userInfo.getValue().accessToken())
                .body(SuccessResponse.of(userInfo.getValue().accessToken()));
    }
}

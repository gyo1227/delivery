package org.delivery.userapi.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.common.api.ApiResponse;
import org.delivery.userapi.domain.user.controller.model.UserResponse;
import org.delivery.userapi.domain.user.model.UserSession;
import org.delivery.userapi.domain.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserResponse> me(Authentication authentication) {
        UserSession userSession = (UserSession) authentication.getPrincipal();
        var response = userService.me(userSession);
        return ApiResponse.Ok(response);
    }

}

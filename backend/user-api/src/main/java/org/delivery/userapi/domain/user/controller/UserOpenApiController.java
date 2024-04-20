package org.delivery.userapi.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.common.api.ApiResponse;
import org.delivery.userapi.domain.user.controller.model.UserRegisterRequest;
import org.delivery.userapi.domain.user.controller.model.UserResponse;
import org.delivery.userapi.domain.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
public class UserOpenApiController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(
            @Valid @RequestBody UserRegisterRequest request
    ) {
        var response = userService.register(request);
        return ApiResponse.Ok(response);
    }
}

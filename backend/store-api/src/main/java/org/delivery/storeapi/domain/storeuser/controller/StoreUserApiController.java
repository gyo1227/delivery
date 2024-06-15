package org.delivery.storeapi.domain.storeuser.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.common.api.ApiResponse;
import org.delivery.storeapi.domain.storeuser.controller.model.StoreUserResponse;
import org.delivery.storeapi.domain.storeuser.model.StoreUserSession;
import org.delivery.storeapi.domain.storeuser.service.StoreUserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store-user")
public class StoreUserApiController {

    private final StoreUserService storeUserService;

    @GetMapping("/me")
    public ApiResponse<StoreUserResponse> me(Authentication authentication) {
        StoreUserSession storeUserSession = (StoreUserSession) authentication.getPrincipal();

        var response = storeUserService.me(storeUserSession);
        return ApiResponse.Ok(response);
    }
}

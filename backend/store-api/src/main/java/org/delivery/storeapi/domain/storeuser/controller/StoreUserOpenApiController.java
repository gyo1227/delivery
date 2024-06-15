package org.delivery.storeapi.domain.storeuser.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.common.api.ApiResponse;
import org.delivery.storeapi.domain.storeuser.controller.model.StoreUserRegisterRequest;
import org.delivery.storeapi.domain.storeuser.controller.model.StoreUserResponse;
import org.delivery.storeapi.domain.storeuser.service.StoreUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/store-user")
public class StoreUserOpenApiController {

    private final StoreUserService storeUserService;

    @PostMapping("/register")
    public ApiResponse<StoreUserResponse> register(
            @Valid @RequestBody StoreUserRegisterRequest request
    ) {
        var response = storeUserService.register(request);
        return ApiResponse.Ok(response);
    }
}

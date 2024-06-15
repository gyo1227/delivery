package org.delivery.storeapi.domain.storeuser.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreUserLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}

package com.example.delivery.domain.user.controller;

import com.example.delivery.domain.user.dto.SignupRequest;
import com.example.delivery.domain.user.dto.SignupResponse;
import com.example.delivery.domain.user.service.UserService;
import com.example.delivery.global.config.security.SecurityConfig;
import com.example.delivery.global.exception.ReasonCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Nested
    @DisplayName("POST /api/auth/sign-up")
    class SignupTest {
        private static final String SIGNUP_URL = "/api/auth/sign-up";

        @Test
        @DisplayName("성공")
        void signupSuccess() throws Exception {
            // given
            SignupRequest request = new SignupRequest("test@test.com", "testpw1234", "test");
            SignupResponse response = new SignupResponse(1L, "test@test.com", "test");
            given(userService.signUp(request)).willReturn(response);

            // when & then
            mockMvc.perform(post(SIGNUP_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("2000"))
                    .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.email").value(request.email()))
                    .andExpect(jsonPath("$.data.nickname").value(request.nickname()));
        }

        @Test
        @DisplayName("실패 - valid error")
        void signupValidError() throws Exception {
            // given
            SignupRequest request = new SignupRequest("notEmail", "errorpw", "");
            String code = String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value() * 10 + ReasonCode.REQUIRED_PARAMETER_VALIDATION_ERROR.getCode());

            // when & then
            mockMvc.perform(post(SIGNUP_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.code").value(code))
                    .andExpect(jsonPath("$.message").value("입력값이 올바르지 않습니다. 각 항목을 확인해주세요."))
                    .andExpect(jsonPath("$.data.email").exists())
                    .andExpect(jsonPath("$.data.password").exists())
                    .andExpect(jsonPath("$.data.nickname").exists());

            then(userService).shouldHaveNoInteractions();
        }
    }
}
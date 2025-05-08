package kr.co.delivery.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.delivery.common.exception.enums.ReasonCode;
import kr.co.delivery.common.exception.enums.StatusCode;
import kr.co.delivery.domain.user.dto.UserSignupRequest;
import kr.co.delivery.domain.user.dto.UserSignupResponse;
import kr.co.delivery.domain.user.exception.UserException;
import kr.co.delivery.domain.user.exception.enums.UserErrorCode;
import kr.co.delivery.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserAuthController.class)
class UserAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    UserService userService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @DisplayName("유저 회원가입")
    @Nested
    class UserSignup {
        @DisplayName("[1] - 필수 입력 값 누락 시 400 BAD_REQUEST 및 필드 에러 메시지를 반환한다.")
        @Test
        void test1() throws Exception {
            // given
            final UserSignupRequest request = new UserSignupRequest("", "", "", "");

            String code = String.valueOf(
                    StatusCode.BAD_REQUEST.getCode() * 10 + ReasonCode.REQUIRED_PARAMETER_VALIDATION_ERROR.getCode());

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/api/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(code))
                    .andExpect(jsonPath("$.message").value("입력값이 올바르지 않습니다. 각 항목을 확인해주세요."))
                    .andExpect(jsonPath("$.data.email").exists())
                    .andExpect(jsonPath("$.data.password").exists())
                    .andExpect(jsonPath("$.data.name").exists())
                    .andExpect(jsonPath("$.data.phone").exists())
                    .andDo(print());
        }

        @DisplayName("[2] - 이메일 유효성 검사 실패 시 400 BAD_REQUEST 및 필드 에러 메시지를 반환한다.")
        @Test
        void test2() throws Exception {
            // given
            final UserSignupRequest request = new UserSignupRequest("errorEmail", "test1234!", "test", "01012341234");

            String code = String.valueOf(
                    StatusCode.BAD_REQUEST.getCode() * 10 + ReasonCode.REQUIRED_PARAMETER_VALIDATION_ERROR.getCode());

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/api/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(code))
                    .andExpect(jsonPath("$.message").value("입력값이 올바르지 않습니다. 각 항목을 확인해주세요."))
                    .andDo(print());
        }

        @DisplayName("[3] - 비밀번호 유효성 검사 실패 시 400 BAD_REQUEST 및 필드 에러 메시지를 반환한다.")
        @Test
        void test3() throws Exception {
            // given
            final UserSignupRequest request = new UserSignupRequest("test@email.com", "errorpassword", "test", "01012341234");

            String code = String.valueOf(
                    StatusCode.BAD_REQUEST.getCode() * 10 + ReasonCode.REQUIRED_PARAMETER_VALIDATION_ERROR.getCode());

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/api/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(code))
                    .andExpect(jsonPath("$.message").value("입력값이 올바르지 않습니다. 각 항목을 확인해주세요."))
                    .andExpect(jsonPath("$.data.password").value("8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해주세요. (적어도 하나의 영문 소문자, 숫자 포함)"))
                    .andDo(print());
        }

        @DisplayName("[4] - 이름 유효성 검사 실패 시 400 BAD_REQUEST 및 필드 에러 메시지를 반환한다.")
        @Test
        void test4() throws Exception {
            // given
            final UserSignupRequest request = new UserSignupRequest("test@email.com", "test1234!", "errorname@$$@", "01012341234");

            String code = String.valueOf(
                    StatusCode.BAD_REQUEST.getCode() * 10 + ReasonCode.REQUIRED_PARAMETER_VALIDATION_ERROR.getCode());

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/api/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(code))
                    .andExpect(jsonPath("$.message").value("입력값이 올바르지 않습니다. 각 항목을 확인해주세요."))
                    .andExpect(jsonPath("$.data.name").value("한글과 영문 대, 소문자만 가능합니다."))
                    .andDo(print());
        }

        @DisplayName("[5] - 전화번호 유효성 검사 실패 시 400 BAD_REQUEST 및 필드 에러 메시지를 반환한다.")
        @Test
        void test5() throws Exception {
            // given
            final UserSignupRequest request = new UserSignupRequest("test@email.com", "test1234!", "test", "010323");

            String code = String.valueOf(
                    StatusCode.BAD_REQUEST.getCode() * 10 + ReasonCode.REQUIRED_PARAMETER_VALIDATION_ERROR.getCode());

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/api/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(code))
                    .andExpect(jsonPath("$.message").value("입력값이 올바르지 않습니다. 각 항목을 확인해주세요."))
                    .andExpect(jsonPath("$.data.phone").value("휴대전화 번호 형식이 올바르지 않습니다. (숫자만 입력해주세요)"))
                    .andDo(print());
        }

        @DisplayName("[6] - 이미 존재하는 이메일로 회원가입 시 409 CONFLICT 응답과 에러 메시지를 반환한다.")
        @Test
        void test6() throws Exception {
            // given
            final UserSignupRequest request = new UserSignupRequest("existUser@email.com", "test1234!", "test", "01012341234");
            given(userService.signup(request))
                    .willThrow(new UserException(UserErrorCode.ALREADY_EXIST_EMAIL));

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/api/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value("4090"))
                    .andExpect(jsonPath("$.message").value(UserErrorCode.ALREADY_EXIST_EMAIL.errorMessage()))
                    .andDo(print());
        }

        @DisplayName("[7] - 이미 존재하는 전화번호로 회원가입 시 409 CONFLICT 응답과 에러 메시지를 반환한다")

        @Test
        void test7() throws Exception {
            // given
            final UserSignupRequest request = new UserSignupRequest("test@email.com", "test1234!", "test", "01012341234");
            given(userService.signup(request))
                    .willThrow(new UserException(UserErrorCode.ALREADY_EXIST_PHONE));

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/api/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.code").value("4090"))
                    .andExpect(jsonPath("$.message").value(UserErrorCode.ALREADY_EXIST_PHONE.errorMessage()))
                    .andDo(print());
        }

        @DisplayName("[8] - 유효한 회원가입 요청 시 200 OK와 회원 정보가 포함된 응답을 반환한다")
        @Test
        void test8() throws Exception {
            // given
            final UserSignupRequest request = new UserSignupRequest("test@email.com", "test1234!", "test", "01012341234");
            final UserSignupResponse response = new UserSignupResponse(1L, request.email(), request.name(), LocalDateTime.now());

            given(userService.signup(any(UserSignupRequest.class))).willReturn(response);

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/api/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("2000"))
                    .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.email").value(request.email()))
                    .andExpect(jsonPath("$.data.name").value(request.name()))
                    .andDo(print());
        }
    }
}
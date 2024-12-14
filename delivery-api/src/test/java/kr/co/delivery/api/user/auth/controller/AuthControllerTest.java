package kr.co.delivery.api.user.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.delivery.api.common.exception.UserErrorException;
import kr.co.delivery.api.common.exception.enums.UserErrorCode;
import kr.co.delivery.api.common.security.jwt.Jwts;
import kr.co.delivery.api.common.util.CookieUtil;
import kr.co.delivery.api.user.auth.controller.dto.SignInReq;
import kr.co.delivery.api.user.auth.controller.dto.SignUpReq;
import kr.co.delivery.api.user.auth.service.UserService;
import kr.co.delivery.common.exception.consts.StatusCode;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private CookieUtil cookieUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .defaultRequest(post("/**").with(csrf()))
                .build();
    }

    @DisplayName("유저 회원가입")
    @Nested
    class signUpUser {

        @DisplayName("[1] - 이메일, 비밀번호, 닉네임 필수 입력")
        @Test
        void test1() throws Exception {
            // given
            final SignUpReq request = new SignUpReq("", "", "");

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/v1/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.code").value("4220"))
                    .andExpect(jsonPath("$.message").value(StatusCode.UNPROCESSABLE_CONTENT.name()))
                    .andExpect(jsonPath("$.fieldErrors.email").exists())
                    .andExpect(jsonPath("$.fieldErrors.password").exists())
                    .andExpect(jsonPath("$.fieldErrors.nickname").exists())
                    .andDo(print());
        }

        @DisplayName("[2] - 이메일 검증")
        @Test
        void test2() throws Exception {
            // given
            final SignUpReq request = new SignUpReq("erroremail", "test1234!", "test");

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/v1/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.code").value("4220"))
                    .andExpect(jsonPath("$.message").value(StatusCode.UNPROCESSABLE_CONTENT.name()))
                    .andExpect(jsonPath("$.fieldErrors.email").value("입력하신 이메일 주소가 형식에 맞지 않습니다. 다시 입력해 주세요"))
                    .andDo(print());
        }

        @DisplayName("[3] - 비밀번호 검증")
        @Test
        void test3() throws Exception {
            // given
            final SignUpReq request = new SignUpReq("test@test.com", "errorpw", "test");

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/v1/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.code").value("4220"))
                    .andExpect(jsonPath("$.message").value(StatusCode.UNPROCESSABLE_CONTENT.name()))
                    .andExpect(jsonPath("$.fieldErrors.password").value("8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해주세요. (적어도 하나의 영문 소문자, 숫자 포함)"))
                    .andDo(print());
        }

        @DisplayName("[4] - 닉네임 검증")
        @Test
        void test4() throws Exception {
            // given
            final SignUpReq request = new SignUpReq("test@test.com", "test1234!", "1234");

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/v1/auth/user/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.code").value("4220"))
                    .andExpect(jsonPath("$.message").value(StatusCode.UNPROCESSABLE_CONTENT.name()))
                    .andExpect(jsonPath("$.fieldErrors.nickname").value("한글과 영문 대, 소문자만 가능합니다"))
                    .andDo(print());
        }

        @DisplayName("[5] 이메일 중복")
        @Test
        void test5() throws Exception {
            // given
            final SignUpReq request = new SignUpReq("existUser@email.com", "test1234!", "test");
            given(userService.signUp(request))
                    .willThrow(new UserErrorException(UserErrorCode.ALREADY_EXIST_EMAIL));

            // when
            final ResultActions resultActions = mockMvc.perform(post("/v1/auth/user/sign-up")
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

        @DisplayName("[6] 정상적인 회원가입")
        @Test
        void test6() throws Exception {
            // given
            final SignUpReq request = new SignUpReq("test@test.com", "test1234!", "test");
            UserEntity entity = UserEntity.builder()
                    .email("test@test.com")
                    .password("test1234!")
                    .nickname("test")
                    .build();
            given(userService.signUp(request)).willReturn(entity);

            // when
            final ResultActions resultActions = mockMvc.perform(post("/v1/auth/user/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.email").value("test@test.com"))
                    .andExpect(jsonPath("$.data.nickname").value("test"))
                    .andDo(print());
        }
    }

    @DisplayName("유저 로그인")
    @Nested
    class signInUser {

        @DisplayName("[1] - 이메일, 비밀번호 필수 입력")
        @Test
        void test1() throws Exception {
            // given
            final SignInReq request = new SignInReq("", "");

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/v1/auth/user/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.code").value("4220"))
                    .andExpect(jsonPath("$.message").value(StatusCode.UNPROCESSABLE_CONTENT.name()))
                    .andExpect(jsonPath("$.fieldErrors.email").exists())
                    .andExpect(jsonPath("$.fieldErrors.password").exists())
                    .andDo(print());
        }

        @DisplayName("[2] - 로그인 실패")
        @Test
        void test2() throws Exception {
            // given
            SignInReq request = new SignInReq("test@email.com", "tqwefqef!");
            given(userService.signIn(any(SignInReq.class)))
                    .willThrow(new UserErrorException(UserErrorCode.INVALID_EMAIL_OR_PASSWORD));

            // when
            final ResultActions resultActions = mockMvc.perform(
                    post("/v1/auth/user/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.code").value("4010"))
                    .andExpect(jsonPath("$.message").value(UserErrorCode.INVALID_EMAIL_OR_PASSWORD.errorMessage()))
                    .andDo(print());
        }

        @DisplayName("[3] - 로그인 성공")
        @Test
        void test3() throws Exception {
            // given
            SignInReq request = new SignInReq("test@email.com", "test1234!");
            ResponseCookie expectedCookie = ResponseCookie.from("refreshToken", "refreshToken")
                    .maxAge(Duration.ofDays(7).toSeconds()).httpOnly(true).path("/").build();

            given(userService.signIn(request))
                    .willReturn(Pair.of(1L, Jwts.of("accessToken", "refreshToken")));
            given(cookieUtil.createCookie("refreshToken", "refreshToken", Duration.ofDays(7).toSeconds()))
                    .willReturn(expectedCookie);

            // when
            ResultActions resultActions = mockMvc.perform(
                    post("/v1/auth/user/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(header().exists("Set-Cookie"))
                    .andExpect(header().string("Authorization", "accessToken"))
                    .andDo(print());
        }
    }
}
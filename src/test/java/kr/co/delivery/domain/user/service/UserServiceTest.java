package kr.co.delivery.domain.user.service;

import kr.co.delivery.domain.user.dto.UserSignupRequest;
import kr.co.delivery.domain.user.dto.UserSignupResponse;
import kr.co.delivery.domain.user.entity.User;
import kr.co.delivery.domain.user.exception.UserException;
import kr.co.delivery.domain.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserJpaRepository userJpaRepository;

    @DisplayName("유저 회원가입")
    @Nested
    class UserSignup {
        String email = "test@email.com";
        String password = "test1234!";
        String name = "test";
        String phone = "01012341234";

        @DisplayName("[1] - 이미 가입된 이메일로 회원가입 시 UserException이 발생한다.")
        @Test
        void test1() throws Exception {
            // given
            UserSignupRequest request = new UserSignupRequest(email, password, name, phone);
            User mockUser = mock(User.class);

            given(userJpaRepository.findByEmail(request.email()))
                    .willReturn(Optional.of(mockUser));

            // when & then
            assertThrows(UserException.class, () -> userService.signup(request));
            then(userJpaRepository).should().findByEmail(request.email());
        }

        @DisplayName("[2] - 이미 가입된 전화번호로 회원가입 시 UserException이 발생한다.")
        @Test
        void test2() throws Exception {
            // given
            UserSignupRequest request = new UserSignupRequest(email, password, name, phone);
            User mockUser = mock(User.class);

            given(userJpaRepository.findByEmail(request.email()))
                    .willReturn(Optional.empty());
            given(userJpaRepository.findByPhone(request.phone()))
                    .willReturn(Optional.of(mockUser));

            // when & then
            assertThrows(UserException.class, () -> userService.signup(request));
            then(userJpaRepository).should().findByPhone(request.phone());
        }

        @DisplayName("[3] - 회원가입 성공")
        @Test
        void test3() throws Exception {
            // given
            UserSignupRequest request = new UserSignupRequest(email, password, name, phone);

            given(userJpaRepository.findByEmail(request.email()))
                    .willReturn(Optional.empty());
            given(userJpaRepository.findByPhone(request.phone()))
                    .willReturn(Optional.empty());
            given(passwordEncoder.encode(request.password()))
                    .willReturn("encodedPassword");

            User entity = User.builder()
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .name(request.name())
                    .phone(request.phone())
                    .build();

            given(userJpaRepository.save(any(User.class)))
                    .willReturn(entity);

            // when
            UserSignupResponse response = userService.signup(request);

            // then
            then(userJpaRepository).should().save(any(User.class));
            assertNotNull(response);
            assertEquals(request.email(), response.email());
            assertEquals(request.name(), response.name());
        }
    }

}
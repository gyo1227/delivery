package com.example.delivery.domain.user.service;

import com.example.delivery.domain.user.dto.SignupRequest;
import com.example.delivery.domain.user.dto.SignupResponse;
import com.example.delivery.domain.user.entity.User;
import com.example.delivery.domain.user.entity.enums.UserRole;
import com.example.delivery.domain.user.exception.UserException;
import com.example.delivery.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("signup")
    class SignupTest {

        @Test
        @DisplayName("이미 존재하는 이메일인 경우 UserException을 던진다.")
        void throwIfEmailAlreadyExists() {
            // given
            SignupRequest request = new SignupRequest("test@test.com", "testpw1234", "test");
            given(userRepository.existsByEmail(request.email())).willReturn(true);

            // when & then
            assertThrows(UserException.class, () -> userService.signUp(request));
            then(userRepository).should().existsByEmail(request.email());
            then(passwordEncoder).shouldHaveNoInteractions();
            then(userRepository).should(times(0)).save(any(User.class));
        }

        @Test
        @DisplayName("신규 이메일인 경우 회원가입을 진행하고 응답을 반환한다.")
        void savesAndReturnResponseWhenNewEmail() {
            // given
            SignupRequest request = new SignupRequest("test@test.com", "testpw1234", "test");
            given(userRepository.existsByEmail(request.email())).willReturn(false);
            given(passwordEncoder.encode(request.password())).willReturn("encodedPw");

            User savedUser = User.builder()
                    .id(1L)
                    .email(request.email())
                    .password("encodedPw")
                    .nickname(request.nickname())
                    .role(UserRole.USER)
                    .build();

            given(userRepository.save(any(User.class))).willReturn(savedUser);

            // when
            SignupResponse response = userService.signUp(request);

            // then
            assertNotNull(response);
            assertEquals(savedUser.getId(), response.id());
            assertEquals(savedUser.getEmail(), response.email());
            assertEquals(savedUser.getNickname(), response.nickname());

            then(userRepository).should().existsByEmail(request.email());
            then(passwordEncoder).should().encode(request.password());
        }
    }

}
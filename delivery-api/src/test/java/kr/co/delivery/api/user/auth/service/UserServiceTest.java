package kr.co.delivery.api.user.auth.service;

import kr.co.delivery.api.common.exception.UserErrorException;
import kr.co.delivery.api.user.auth.controller.dto.SignUpReq;
import kr.co.delivery.db.domains.user.domain.Provider;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import kr.co.delivery.db.domains.user.repository.UserJpaRepository;
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
    class signUpUser {
        private final String password = "test1234!";
        private final String nickname = "test";

        @DisplayName("회원가입 시, 사용중인 이메일일 경우 `이미 존재하는 이메일입니다` 응답을 반환")
        @Test
        void test1() throws Exception {
            // given
            String existEmail = "exist@email.com";
            SignUpReq request = new SignUpReq(existEmail, password, nickname);

            UserEntity userEntity = UserEntity.builder()
                    .email(existEmail)
                    .password(passwordEncoder.encode(password))
                    .nickname(nickname)
                    .provider(Provider.LOCAL)
                    .build();

            given(userJpaRepository.findByEmailAndProvider(existEmail, Provider.LOCAL))
                    .willReturn(Optional.of(userEntity));

            // when
            assertThrows(UserErrorException.class, () -> userService.signUp(request));
        }

        @DisplayName("회원가입 시, 사용중인 이메일이 아닐 경우 회원가입 진행")
        @Test
        void test2() throws Exception {
            // given
            String email = "test@test.com";
            String encodedPassword = "encodedPassword";
            SignUpReq request = new SignUpReq(email, password, nickname);

            given(passwordEncoder.encode(password))
                    .willReturn(encodedPassword);

            UserEntity userEntity = UserEntity.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .nickname(nickname)
                    .provider(Provider.LOCAL)
                    .build();

            given(userJpaRepository.findByEmailAndProvider(email, Provider.LOCAL))
                    .willReturn(Optional.empty());

            given(userJpaRepository.save(any(UserEntity.class)))
                    .willReturn(userEntity);

            // when
            UserEntity savedUser = userService.signUp(request);

            // then
            assertNotNull(savedUser);
            assertEquals(email, savedUser.getEmail());
            assertEquals(encodedPassword, savedUser.getPassword());
            assertEquals(nickname, savedUser.getNickname());
        }
    }

}
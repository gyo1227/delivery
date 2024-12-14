package kr.co.delivery.api.user.auth.service;

import kr.co.delivery.api.common.exception.UserErrorException;
import kr.co.delivery.api.common.security.jwt.Jwts;
import kr.co.delivery.api.config.fixture.UserFixture;
import kr.co.delivery.api.user.auth.controller.dto.SignInReq;
import kr.co.delivery.api.user.auth.controller.dto.SignUpReq;
import kr.co.delivery.db.domains.user.domain.Provider;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import kr.co.delivery.db.domains.user.repository.UserJpaRepository;
import org.apache.commons.lang3.tuple.Pair;
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
    private JwtService jwtService;

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

    @DisplayName("유저 로그인")
    @Nested
    class signInUser {

        @DisplayName("로그인 시, email에 해당하는 유저가 존재하지 않으면 UserErrorException을 반환한다")
        @Test
        void test1() throws Exception {
            // given
            String email = "nonExistUser@email.com";
            String password = "test1234!";
            SignInReq request = new SignInReq(email, password);

            given(userJpaRepository.findByEmailAndProvider(any(), any()))
                    .willReturn(Optional.empty());

            // when
            assertThrows(UserErrorException.class, () -> userService.signIn(request));
        }

        @DisplayName("로그인 시, 유저가 존재하고 비밀번호가 일치하지 않으면 UserErrorException을 반환한다")
        @Test
        void test2() throws Exception {
            // given
            UserEntity userEntity = UserFixture.USER.toUserEntity();
            String password = "noMatchPW!";
            SignInReq request = new SignInReq(userEntity.getEmail(), password);

            given(userJpaRepository.findByEmailAndProvider(userEntity.getEmail(), userEntity.getProvider()))
                    .willReturn(Optional.of(userEntity));
            given(passwordEncoder.matches(password, userEntity.getPassword()))
                    .willReturn(false);

            // when
            assertThrows(UserErrorException.class, () -> userService.signIn(request));
        }

        @DisplayName("로그인 시, 유저가 존재하고 비밀번호가 일치하면 id와 token을 반환한다.")
        @Test
        void test3() throws Exception {
            // given
            UserEntity userEntity = UserFixture.USER.toUserEntity();
            SignInReq request = new SignInReq(userEntity.getEmail(), userEntity.getPassword());

            given(userJpaRepository.findByEmailAndProvider(userEntity.getEmail(), userEntity.getProvider()))
                    .willReturn(Optional.of(userEntity));
            given(passwordEncoder.matches(userEntity.getPassword(), userEntity.getPassword()))
                    .willReturn(true);
            given(jwtService.createToken(userEntity))
                    .willReturn(Jwts.of("accessToken", "refreshToken"));

            // when
            Pair<Long, Jwts> result = userService.signIn(request);

            assertEquals(userEntity.getId(), result.getKey());
            assertEquals("accessToken", result.getValue().accessToken());
            assertEquals("refreshToken", result.getValue().refreshToken());
        }
    }
}
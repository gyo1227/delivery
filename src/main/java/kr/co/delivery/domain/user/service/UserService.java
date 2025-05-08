package kr.co.delivery.domain.user.service;

import kr.co.delivery.domain.user.dto.UserSignupRequest;
import kr.co.delivery.domain.user.dto.UserSignupResponse;
import kr.co.delivery.domain.user.entity.User;
import kr.co.delivery.domain.user.exception.UserException;
import kr.co.delivery.domain.user.exception.enums.UserErrorCode;
import kr.co.delivery.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public UserSignupResponse signup(UserSignupRequest request) {
        if(userJpaRepository.findByEmail(request.email()).isPresent()) {
            log.warn("이미 가입된 이메일입니다. email: {}", request.email());
            throw new UserException(UserErrorCode.ALREADY_EXIST_EMAIL);
        }

        if(userJpaRepository.findByPhone(request.phone()).isPresent()) {
            log.warn("이미 가입된 전화번호입니다. phone: {}", request.phone());
            throw new UserException(UserErrorCode.ALREADY_EXIST_PHONE);
        }

        log.info("새로운 회원입니다. email: {}, phone: {}", request.email(), request.phone());

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .phone(request.phone())
                .build();

        User savedUser = userJpaRepository.save(user);

        return UserSignupResponse.from(savedUser);
    }
}

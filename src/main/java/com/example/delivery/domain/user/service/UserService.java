package com.example.delivery.domain.user.service;

import com.example.delivery.domain.user.dto.SignupRequest;
import com.example.delivery.domain.user.dto.SignupResponse;
import com.example.delivery.domain.user.entity.User;
import com.example.delivery.domain.user.exception.UserException;
import com.example.delivery.domain.user.exception.enums.UserErrorCode;
import com.example.delivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse signUp(SignupRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            log.warn("{}: {}", UserErrorCode.ALREADY_EXIST_EMAIL, request.email());
            throw new UserException(UserErrorCode.ALREADY_EXIST_EMAIL);
        }

        log.info("새로운 회원입니다: {}", request.email());
        User user = userRepository.save(User.from(request, passwordEncoder));

        return SignupResponse.from(user);
    }


}

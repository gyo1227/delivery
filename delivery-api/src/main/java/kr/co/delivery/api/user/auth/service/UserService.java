package kr.co.delivery.api.user.auth.service;

import kr.co.delivery.api.common.exception.UserErrorException;
import kr.co.delivery.api.common.exception.enums.UserErrorCode;
import kr.co.delivery.api.common.security.jwt.Jwts;
import kr.co.delivery.api.user.auth.controller.dto.SignInReq;
import kr.co.delivery.api.user.auth.controller.dto.SignUpReq;
import kr.co.delivery.db.domains.user.domain.Provider;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import kr.co.delivery.db.domains.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public UserEntity signUp(SignUpReq request) {
        UserEntity user;
        Optional<UserEntity> userEntity = userJpaRepository.findByEmailAndProvider(request.email(), Provider.LOCAL);

        if(userEntity.isPresent()) {
            log.warn("이미 가입된 이메일입니다. email: {}", userEntity.get().getEmail());
            throw new UserErrorException(UserErrorCode.ALREADY_EXIST_EMAIL);
        } else {
            log.info("새로운 회원입니다. email: {}", request.email());
            user = userJpaRepository.save(request.toEntity(passwordEncoder));
        }
        return user;
    }

    @Transactional(readOnly = true)
    public Pair<Long, Jwts> signIn(SignInReq request) {
        Optional<UserEntity> user = userJpaRepository.findByEmailAndProvider(request.email(), Provider.LOCAL);

        if(user.isEmpty()) {
            log.warn("해당 유저가 존재하지 않습니다. email: {}", request.email());
            throw new UserErrorException(UserErrorCode.INVALID_EMAIL_OR_PASSWORD);
        }

        if(!passwordEncoder.matches(request.password(), user.get().getPassword())) {
            log.warn("비밀번호가 일치하지 않습니다. email: {}", request.email());
            throw new UserErrorException(UserErrorCode.INVALID_EMAIL_OR_PASSWORD);
        }

        return Pair.of(user.get().getId(), jwtService.createToken(user.get()));
    }
}

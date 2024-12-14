package kr.co.delivery.api.user.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.delivery.db.domains.user.domain.Provider;
import kr.co.delivery.db.domains.user.domain.Role;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public record SignUpReq(
        @NotBlank(message = "이메일를 입력해주세요")
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "입력하신 이메일 주소가 형식에 맞지 않습니다. 다시 입력해 주세요"
        )
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,16}$",
                message = "8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해주세요. (적어도 하나의 영문 소문자, 숫자 포함)"
        )
        String password,
        @NotBlank(message = "닉네임을 입력해주세요")
        @Pattern(
                regexp = "^[가-힣a-zA-Z]{2,8}$",
                message = "한글과 영문 대, 소문자만 가능합니다"
        )
        String nickname
) {
    public UserEntity toEntity(PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(Role.USER)
                .provider(Provider.LOCAL)
                .build();
    }
}

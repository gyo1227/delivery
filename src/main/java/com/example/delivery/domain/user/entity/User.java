package com.example.delivery.domain.user.entity;

import com.example.delivery.domain.user.dto.SignupRequest;
import com.example.delivery.domain.user.entity.enums.UserRole;
import com.example.delivery.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public static User from(SignupRequest request, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .role(UserRole.USER)
                .build();
    }
}

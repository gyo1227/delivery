package kr.co.delivery.domain.user.entity;

import jakarta.persistence.*;
import kr.co.delivery.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 20, unique = true, nullable = false)
    private String phone;

    @Column(length = 150)
    private String address;

    @Builder
    public User(Long id, String email, String password, String name, String phone, String address) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("email은 null이거나 빈 문자열이 될 수 없습니다.");
        } else if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("password는 null이거나 빈 문자열이 될 수 없습니다.");
        } else if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("name은 null이거나 빈 문자열이 될 수 없습니다.");
        } else if (!StringUtils.hasText(phone)) {
            throw new IllegalArgumentException("phone은 null이거나 빈 문자열이 될 수 없습니다.");
        }

        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}

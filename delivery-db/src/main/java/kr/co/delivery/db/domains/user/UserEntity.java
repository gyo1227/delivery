package kr.co.delivery.db.domains.user;


import jakarta.persistence.*;
import kr.co.delivery.db.domains.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder
    public UserEntity(Long id, String email, String password, String nickname, String address, Role role, Provider provider) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.address = address;
        this.role = role;
        this.provider = provider;
    }
}

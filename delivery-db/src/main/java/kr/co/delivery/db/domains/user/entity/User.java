package kr.co.delivery.db.domains.user.entity;

import jakarta.persistence.*;
import kr.co.delivery.db.domains.BaseEntity;
import kr.co.delivery.db.domains.user.entity.enums.Provider;
import kr.co.delivery.db.domains.user.entity.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 150)
    private String address;

    @Column(length = 20, unique = true)
    private String phone;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder
    public User(Long id, String email, String password, String name, String address, String phone, Role role, Provider provider) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.provider = provider;
    }
}

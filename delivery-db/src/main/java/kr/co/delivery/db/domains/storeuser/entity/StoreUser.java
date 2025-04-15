package kr.co.delivery.db.domains.storeuser.entity;

import jakarta.persistence.*;
import kr.co.delivery.db.domains.BaseEntity;
import kr.co.delivery.db.domains.user.entity.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 20, unique = true)
    private String phone;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public StoreUser(Long id, String email, String password, String name, String phone, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}

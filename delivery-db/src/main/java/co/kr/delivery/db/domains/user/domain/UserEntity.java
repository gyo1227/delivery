package co.kr.delivery.db.domains.user.domain;

import co.kr.delivery.db.domains.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "user")
@Getter
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String address;

    private Provider provider;
}

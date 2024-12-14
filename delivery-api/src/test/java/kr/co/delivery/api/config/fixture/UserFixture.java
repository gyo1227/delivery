package kr.co.delivery.api.config.fixture;

import kr.co.delivery.db.domains.user.domain.Provider;
import kr.co.delivery.db.domains.user.domain.Role;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import lombok.Getter;

@Getter
public enum UserFixture {
    USER(1L, "testUser@email.com", "test1234!", "testUser", Role.USER, Provider.LOCAL);

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final Role role;
    private final Provider provider;

    UserFixture(Long id, String email, String password, String nickname, Role role, Provider provider) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
    }

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(role)
                .provider(provider)
                .build();
    }
}

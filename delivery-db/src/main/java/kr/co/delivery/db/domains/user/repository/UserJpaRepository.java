package kr.co.delivery.db.domains.user.repository;

import kr.co.delivery.db.domains.user.domain.Provider;
import kr.co.delivery.db.domains.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndProvider(String email, Provider provider);
}

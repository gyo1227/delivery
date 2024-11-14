package co.kr.delivery.db.domains.user.repository;

import co.kr.delivery.db.domains.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

}

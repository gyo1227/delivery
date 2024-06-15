package org.delivery.db.storeuser;

import org.delivery.db.storeuser.enums.StoreUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreUserRepository extends JpaRepository<StoreUserEntity, Long> {

    Optional<StoreUserEntity> findByEmail(String email);

    Optional<StoreUserEntity> findFirstByEmailOrderByIdDesc(String email);

    Optional<StoreUserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email, StoreUserStatus status);

    Optional<StoreUserEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreUserStatus status);
}

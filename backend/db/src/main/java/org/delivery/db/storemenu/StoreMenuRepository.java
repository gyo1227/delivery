package org.delivery.db.storemenu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreMenuRepository extends JpaRepository<StoreMenuEntity, Long> {

    List<StoreMenuEntity> findAllByStoreIdOrderByLikeCountDesc(Long storeId);
}

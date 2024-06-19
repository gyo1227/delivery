package org.delivery.db.store;

import org.delivery.db.store.enums.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    List<StoreEntity> findAllByCategoryOrderByStarDesc(StoreCategory category);
}

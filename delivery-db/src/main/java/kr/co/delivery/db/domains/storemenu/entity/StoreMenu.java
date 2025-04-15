package kr.co.delivery.db.domains.storemenu.entity;

import jakarta.persistence.*;
import kr.co.delivery.db.domains.BaseEntity;
import kr.co.delivery.db.domains.store.entity.Store;
import kr.co.delivery.db.domains.storemenu.entity.enums.StoreMenuStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "store_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal price;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreMenuStatus status;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    @Builder
    public StoreMenu(Long id, Store store, String name, BigDecimal price, StoreMenuStatus status, String thumbnailUrl) {
        this.id = id;
        this.store = store;
        this.name = name;
        this.price = price;
        this.status = status;
        this.thumbnailUrl = thumbnailUrl;
    }
}

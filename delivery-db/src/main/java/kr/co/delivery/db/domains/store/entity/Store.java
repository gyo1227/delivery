package kr.co.delivery.db.domains.store.entity;

import jakarta.persistence.*;
import kr.co.delivery.db.domains.BaseEntity;
import kr.co.delivery.db.domains.store.entity.enums.StoreCategory;
import kr.co.delivery.db.domains.store.entity.enums.StoreStatus;
import kr.co.delivery.db.domains.storeuser.entity.StoreUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private StoreUser owner;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal minimumDeliveryPrice;

    @Builder
    public Store(Long id, StoreUser owner, String name, String address, String phone, StoreStatus status, StoreCategory category, BigDecimal minimumDeliveryPrice) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.category = category;
        this.minimumDeliveryPrice = minimumDeliveryPrice;
    }
}

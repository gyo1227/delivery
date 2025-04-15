package kr.co.delivery.db.domains.userorder.entity;

import jakarta.persistence.*;
import kr.co.delivery.db.domains.BaseEntity;
import kr.co.delivery.db.domains.store.entity.Store;
import kr.co.delivery.db.domains.user.entity.User;
import kr.co.delivery.db.domains.userorder.entity.enums.UserOrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal totalPrice;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserOrderStatus status;

    private LocalDateTime orderedAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime cookingStartedAt;

    private LocalDateTime deliveryStartedAt;

    private LocalDateTime receivedAt;
}

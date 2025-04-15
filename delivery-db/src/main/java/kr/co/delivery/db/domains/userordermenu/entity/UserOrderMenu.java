package kr.co.delivery.db.domains.userordermenu.entity;

import jakarta.persistence.*;
import kr.co.delivery.db.domains.BaseEntity;
import kr.co.delivery.db.domains.storemenu.entity.StoreMenu;
import kr.co.delivery.db.domains.userorder.entity.UserOrder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOrderMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_order_id")
    private UserOrder userOrder;

    @ManyToOne
    @JoinColumn(name = "store_menu_id")
    private StoreMenu storeMenu;

    private Integer quantity;
}

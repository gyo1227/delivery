create table `user`
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    nickname   VARCHAR(255) NOT NULL,
    role       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP NULL,
    primary key (id)
) engine=InnoDB;

create table `store`
(
    id              BIGINT         NOT NULL AUTO_INCREMENT,
    owner_id        BIGINT         NOT NULL,
    name            VARCHAR(255)   NOT NULL,
    address         VARCHAR(255)   NOT NULL,
    status          VARCHAR(255)   NOT NULL,
    category        VARCHAR(255)   NOT NULL,
    min_order_price DECIMAL(10, 2) NOT NULL,
    created_at      TIMESTAMP      NOT NULL,
    updated_at      TIMESTAMP,
    primary key (id),
    constraint fk_store_owner foreign key (owner_id) references `user` (id)
) engine=InnoDB;

create table `menu`
(
    id         BIGINT         NOT NULL AUTO_INCREMENT,
    store_id   BIGINT         NOT NULL,
    name       VARCHAR(255)   NOT NULL,
    image_url  VARCHAR(255),
    price      DECIMAL(10, 2) NOT NULL,
    status     VARCHAR(255)   NOT NULL,
    created_at TIMESTAMP      NOT NULL,
    updated_at TIMESTAMP,
    primary key (id),
    constraint fk_menu_store foreign key (store_id) references store (id)
) engine=InnoDB;

create table `order`
(
    id               BIGINT         NOT NULL AUTO_INCREMENT,
    user_id          BIGINT         NOT NULL,
    store_id         BIGINT         NOT NULL,
    delivery_address VARCHAR(255)   NOT NULL,
    total_price      DECIMAL(10, 2) NOT NULL,
    status           VARCHAR(255)   NOT NULL,
    ordered_at       TIMESTAMP      NOT NULL,
    accepted_at       TIMESTAMP,
    cooked_at       TIMESTAMP,
    delivered_at     TIMESTAMP,
    primary key (id),
    constraint fk_order_user foreign key (user_id) references `user` (id),
    constraint fk_order_store foreign key (store_id) references store (id)
) engine=InnoDB;

create table `order_menu`
(
    id         BIGINT         NOT NULL AUTO_INCREMENT,
    order_id   BIGINT         NOT NULL,
    menu_id    BIGINT         NOT NULL,
    quantity   INT            NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP      NOT NULL,
    updated_at TIMESTAMP,
    primary key (id),
    constraint fk_order_menu_order foreign key (order_id) references `order` (id),
    constraint fk_order_menu_menu foreign key (menu_id) references menu (id)
) engine=InnoDB;

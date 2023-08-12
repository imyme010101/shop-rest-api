CREATE TABLE members (
    id char(12) not null, 
    password varchar(100) not null,
    roles varchar(9) not null DEFAULT 'U',
    name char(20) not null,
    date_of_birth date not null, 
    phone_number varchar(13) not null, 
    email char(32) not null,
    zip_code varchar(7) not null, 
    address varchar(100) not null,
    gender ENUM('MALE','FEMALE') not null,
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'ENABLE',
    delete_at TIMESTAMP DEFAULT '0000-00-00 00:00:00',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY(id), 
    UNIQUE KEY(phone_number)
);

INSERT INTO members (id,password,roles,name,date_of_birth,phone_number,email,zip_code,address,gender,status) VALUES('test','test','U','test','2000-01-01','010-0000-0000','test@test','123456','test','M','E','2019-01-01 00:00:00');
INSERT INTO members (id,password,roles,name,date_of_birth,phone_number,email,zip_code,address,gender,status) VALUES('admin','admin','A','admin','2000-01-01','010-0000-0000','admin@admin','123456','admin','M','E','2019-01-01 00:00:00');

CREATE TABLE roles (
    role char(1) not null,
    role_name char(12) not null
);

INSERT INTO roles VALUES('U','일반사용자');
INSERT INTO roles VALUES('C','공공기관');
INSERT INTO roles VALUES('A','관리자');

CREATE TABLE members_coupon (
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    coupons_idx int(11) not null, 
    member_id char(12) not null,
    used_at TIMESTAMP DEFAULT '0000-00-00 00:00:00'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO members_coupon  VALUES(1,'test');

CREATE TABLE coupons (
    idx int(11) PRIMARY KEY AUTO_INCREMENT, 
    title varchar(50) not null,
    content varchar(50) not null,
    discount_rate int(11) not null DEFAULT 0,
    discount_price int(11) not null DEFAULT 0,
    start_date TIMESTAMP not null, 
    end_date TIMESTAMP null DEFAULT '0000-00-00 00:00:00', 
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'ENABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idx) USING BTREE
);

INSERT INTO coupons (title,content,discount_rate,discount_price,start_date,end_date,status) VALUES('할인쿠폰 퍼센트','할인쿠폰',20,0,'2019-01-01 00:00:00','0000-00-00 00:00:00','ENABLE');
INSERT INTO coupons (title,content,discount_rate,discount_price,start_date,end_date,status) VALUES('할인쿠폰 가격','할인쿠폰',0,1000,'2019-01-01 00:00:00','2019-01-01 00:00:00','ENABLE');

/*
*/
CREATE TABLE products {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    title varchar(100) null,
    content text null,
    info text null,
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'ENABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idx) USING BTREE
}

INSERT INTO products (title,option_name,option_title,content,info,price,discount_rate,discount_price) VALUES('청바지','','','스키니 청바지','{"제조사": "리바이스"}',25000,1000,0);

CREATE TABLE product_options {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    parent_idx int(11) not null comment '상품 idx',
    depth_type char(1) not null comment '0:옵션이 없을때, 1: 개별 옵션, 2: 존재하는 옵션 전체 선택',
    depth varchar(15) not null DEFAULT '000000000000000' comment '오른쪽 부터 3자리씩 1 뎁스 시작 기본 5뎁스 까지 허용',
    name varchar(100) null DEFAULT '상품' comment '옵션이 아닌경우 상품 옵션은 color, size 등',
    title varchar(100) null DEFAULT '상품' comment '옵션이 아닌경우 상품 옵션은 노랑, 280cm 등',
    price int(11) null DEFAULT 0 DEFAULT 0 comment '상품인 경우 가격 옵션인 경우 0~ 옵션 가격',
    discount_rate int(11) not null DEFAULT 0,
    discount_price int(11) not null DEFAULT 0,
    stock int(11) not null DEFAULT 0,
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'ENABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idx) USING BTREE
    INDEX parent_idx (parent_idx)
}

INSERT INTO products_option (parent_idx,name,title,price,discount_rate,discount_price,stock) VALUES(1,'color','color',0,0,0,0);

/* 부분 취소인 경우  CANCEL 처리후 다시 REORDER 아세 취소면 CANCEL INSERT */
CREATE INTO orders {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    member_id char(12) not null,
    products_idx int(11) not null,
    price int(11) not null,
    order_memo varchar(255) null,
    addressee varchar(20) not null,
    address varchar(200) not null,
    number varchar(13) not null,
    status ENUM('ORDER','CANCEL','REORDER') not null DEFAULT 'ORDER',
    delivery_status ENUM('DELIVERED','WAIT_DELIVERD','READY_DELIVERED','PLAY_DELIVERED') not null DEFAULT 'WAIT_DELIVERD',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idx) USING BTREE
    INDEX member_id (member_id)
}

INSERT INTO orders (member_id,price,order_memo,addressee,address,number) VALUES('test',25000,'test','test','test','test', 'CANCEL');
INSERT INTO orders (member_id,price,order_memo,addressee,address,number) VALUES('test',24000,'test','test','test','test','REORDER');


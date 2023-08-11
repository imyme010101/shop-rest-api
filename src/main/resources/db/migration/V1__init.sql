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
모양, 색상 옵션인 경우 option_name, option_title 입력
price는 최초 부모의 값+옵셥값 섹렉트시 노출 영력을 위해 따로 계산후 AS option_price
WHERE 절 에서 substr 최초의 부모 join
*/
CREATE TABLE product_items {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    parent_idx varchar(10) not null,
    title varchar(100) null,
    option_name varchar(100) null,
    option_title varchar(100) null,
    content text null,
    info text null,
    price int(11) null DEFAULT 0,
    discount_rate int(11) not null DEFAULT 0,
    discount_price int(11) not null DEFAULT 0,
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'ENABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idx) USING BTREE
    INDEX parent_idx (parent_idx)
}

INSERT INTO product_items (parent_idx,title,option_name,option_title,content,info,price,discount_rate,discount_price) VALUES('1','청바지','','','스키니 청바지','{"제조사": "리바이스"}',25000,1000,0);
INSERT INTO product_items (parent_idx,title,option_name,option_title,content,info,price,discount_rate,discount_price) VALUES('11','','데미지','데미지1','스키니 청바지 데미지1','',26000,0,0);
INSERT INTO product_items (parent_idx,title,option_name,option_title,content,info,price,discount_rate,discount_price) VALUES('111','','데미지 모양','데미지 모양1','스키니 청바지 데미지 모양1','',26000,0,0);

/* 부분 취소인 경우  CANCEL 처리후 다시 REORDER 아세 취소면 CANCEL INSERT */
CREATE INTO orders {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    member_id char(12) not null,
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


CREATE TABLE member (
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

INSERT INTO member (id,password,roles,name,date_of_birth,phone_number,email,zip_code,address,gender,status) VALUES('test','test','U','test','2000-01-01','010-0000-0000','test@test','123456','test','M','E','2019-01-01 00:00:00');
INSERT INTO member (id,password,roles,name,date_of_birth,phone_number,email,zip_code,address,gender,status) VALUES('admin','admin','A','admin','2000-01-01','010-0000-0000','admin@admin','123456','admin','M','E','2019-01-01 00:00:00');

CREATE TABLE role (
    role char(1) not null,
    role_name char(12) not null
);

INSERT INTO role VALUES('U','일반사용자');
INSERT INTO role VALUES('C','공공기관');
INSERT INTO role VALUES('A','관리자');

CREATE TABLE member_coupon (
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    coupon_idx int(11) not null, 
    member_id char(12) not null,
    used_at TIMESTAMP DEFAULT '0000-00-00 00:00:00'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO members_coupon  VALUES(1,'test');

CREATE TABLE coupon (
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

INSERT INTO coupon (title,content,discount_rate,discount_price,start_date,end_date,status) VALUES('할인쿠폰 퍼센트','할인쿠폰',20,0,'2019-01-01 00:00:00','0000-00-00 00:00:00','ENABLE');
INSERT INTO coupon (title,content,discount_rate,discount_price,start_date,end_date,status) VALUES('할인쿠폰 가격','할인쿠폰',0,1000,'2019-01-01 00:00:00','2019-01-01 00:00:00','ENABLE');

CREATE TABLE product_category {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    title varchar(100) null,
    depth varchar(150) not null DEFAULT '00' comment '00000000 2자리씩 뎁스 표현',
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'ENABLE',
};


CREATE TABLE product {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    title varchar(100) null,
    content text null,
    info text null,
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'ENABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
};

INSERT INTO product (title,content,info,status) VALUES('청바지','리바이스 청바지 입니다.','{company:"리바이스}','ENABLE');

CREATE TABLE product_option {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    product_idx int(11) not null comment '상품 idx',
    name varchar(100) null,
    title varchar(100) null,
    depth varchar(150) not null DEFAULT '00' comment '00000000 2자리씩 뎁스 표현',
    type char(1) not null comment 'I: 개별 옵션, R: 필수',
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'ENABLE',
    INDEX product_idx (product_idx),
    comment 'option 테이블은 product 테이블가 1:n로 연결되어 있습니다.'
};

CREATE TABLE product_item {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    product_idx int(11) not null comment '상품 idx',
    option_idx int(11) null DEFAULT 0 comment '카테고리 idx',
    price int(11) null DEFAULT 0 DEFAULT 0 comment '상품인 경우 가격 옵션인 경우 0~ 옵션 가격',
    discount_rate int(11) not null DEFAULT 0,
    discount_price int(11) not null DEFAULT 0,
    stock int(11) not null DEFAULT 0 comment '재고 수량',
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'ENABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX product_idx (product_idx),
    INDEX option_idx (option_idx),
    comment 'item 테이블은 product, option 테이블과 n:1로 연결되어 있습니다.'
};



/* 부분 취소인 경우  CANCEL 처리후 다시 REORDER 아세 취소면 CANCEL INSERT */
CREATE INTO order {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    member_id char(12) not null,
    option_idx int(11) not null,
    price int(11) not null,
    order_memo varchar(255) null,
    addressee varchar(20) not null,
    address varchar(200) not null,
    number varchar(13) not null,
    status varchar(10) not null DEFAULT 'ORDER' comment 'ORDER, CANCEL',
    delivery_status varchar(20) not null DEFAULT 'READY' comment 'DELIVERED, WAIT, READY, PLAY, COMPLETED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    INDEX member_id (member_id)
};

CREATE TABLE review {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    member_id char(12) not null,
    product_item_idx int(11) not null,
    subject varchar(255) not null,
    content text not null,
    status ENUM('ENABLE','DISABLE') not null DEFAULT 'DISABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    modifyd_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
};

CREATE TABLE board {
    id char(10) PRIMARY KEY,
    subject varchar(100) not null
}

CREATE TABLE board_write {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    subject varchar(100) not null,
    content text not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
}

CREATE TABLE board_comment {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    board_write_idx int(11) not null,
    parent_idx int(11) not null,
    member_id char(12) not null,
    parent_member_id char(12) not null,
    content text not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
}

CREATE TABLE board_file {
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    type char(1) not null DEFAULT 'C: comment W: write',
    parent_idx int(11) not null,
    file_name varchar(200) not null,
    org_file_name varchar(200) not null,
    order int(11) not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
}
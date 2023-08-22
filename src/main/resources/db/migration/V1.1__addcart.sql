CREATE TABLE product_file (
  idx int(11) PRIMARY KEY AUTO_INCREMENT,
  type char(1) not null COMMENT 'P: 상품 O: 옵션',
  parent_idx int(11) not null,
  file_name varchar(200) not null,
  org_file_name varchar(200) not null,
  sort int(11) null DEFAULT 0,
  created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wish (
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    member_id char(12) not null,
    info_idx int(11) not null,
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    INDEX member_id (member_id),
    INDEX item_idx (info_idx)
);

CREATE TABLE cart (
    idx int(11) PRIMARY KEY AUTO_INCREMENT,
    member_id char(12) not null,
    item_idx int(11) not null,
    stock int(11) not null DEFAULT 1 comment '카트 수량',
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    INDEX member_id (member_id),
    INDEX item_idx (item_idx)
);


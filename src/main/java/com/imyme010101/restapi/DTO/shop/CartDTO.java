package com.imyme010101.restapi.DTO.shop;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
  private int idx;
  private String memberId;
  private int itemIdx;
  private int stock;
  private Timestamp createdAt;
}
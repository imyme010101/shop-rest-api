package com.imyme010101.restapi.DTO.shop.product;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemDTO {
    private int idx;
    private int infoIdx;
    private int optionIdx;
    private int price;
    private int discountRate;
    private int discountPrice;
    private int stock;
    private Status status;
    private LocalDateTime createdAt;

  private enum Status {
    ENABLE, DISABLE
  }
}

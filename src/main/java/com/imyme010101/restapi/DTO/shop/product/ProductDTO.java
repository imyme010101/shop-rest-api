package com.imyme010101.restapi.DTO.shop.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
  private int idx;
  private String title;
  private String content;
  private String info;
  private Status status;

  private enum Status {
    ENABLE, DISABLE
  }
}

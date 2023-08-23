package com.imyme010101.restapi.DTO.shop.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDTO {
  private int idx;
  private int infoIdx;
  private String name;
  private String title;
  private String depth;
  private String type;
  private Status status;

  private enum Status {
    ENABLE, DISABLE
  }
}

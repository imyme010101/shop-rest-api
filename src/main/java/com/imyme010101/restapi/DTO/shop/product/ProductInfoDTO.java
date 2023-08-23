package com.imyme010101.restapi.DTO.shop.product;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInfoDTO {
  private int idx;
  private String title;
  private String content;
  private String info;
  private Status status;
  private LocalDateTime createdAt;

  private enum Status {
    ENABLE, DISABLE
  }
}

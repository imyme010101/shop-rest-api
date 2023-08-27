package com.imyme010101.restapi.DTO.shop;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
  private int idx;
  @NotEmpty
  @Parameter(description = "회원 ID", example = "abcd")
  private String memberId;
  @NotEmpty
  @Parameter(description = "상품(옵션) idx", example = "1")
  private int itemIdx;
  @NotEmpty
  @Parameter(description = "갯수", example = "1")
  private int stock;
  private LocalDateTime createdAt;
}
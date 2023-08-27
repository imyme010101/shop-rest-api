package com.imyme010101.restapi.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imyme010101.restapi.DTO.shop.CartDTO;
import com.imyme010101.restapi.common.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "장바구니", description = "상품 정보 장바구니")
@Slf4j
@Validated
@RestController
@RequestMapping("/cart")
public class CartController {
  private int status;
  private String message = "";
  private Object data = null;

  public ResponseEntity<ApiResponse> add(@ParameterObject @Valid CartDTO cartDTO) {
    
    return ResponseEntity.badRequest().body(ApiResponse.builder()
        .status(this.status)
        .resultMsg(this.message)
        .result(this.data)
        .build());
  }
}

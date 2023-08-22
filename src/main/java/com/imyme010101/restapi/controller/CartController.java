package com.imyme010101.restapi.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "장바구니", description = "상품 정보 장바구니")
@Slf4j
@Validated
@RestController
@RequestMapping("/cart")
public class CartController {
  
}

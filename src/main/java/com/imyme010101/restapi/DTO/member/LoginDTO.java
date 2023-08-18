package com.imyme010101.restapi.DTO.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class LoginDTO {
  @NotEmpty
  @Pattern(regexp = "^[a-zA-Z0-9]+$")
  @Schema(description = "영문 숫자 조합", defaultValue = "abcd1234")
  public String id;

  @NotEmpty
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
  @Schema(description = "영문, 특수문자 8자 이상 20자 이하", defaultValue = "abcd12#$12#$")
  public String password;
}

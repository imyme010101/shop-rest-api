package com.imyme010101.restapi.DTO.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class LoginDTO {
  @NotEmpty
  @Pattern(regexp = "^[a-zA-Z0-9]+$")
  @Schema(description = "\uC601\uBB38\uC22B\uC790", defaultValue = "abcd1234")
  public String id;

  @NotEmpty
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$") // 영문, 특수문자 8자 이상 20자 이하
  @Schema(description = "\uC601\uBB38, \uD2B9\uC2A4\uBB38\uC790 8\uC790 \uC774\uC0C1 20\uC790 \uC774\uD558", defaultValue = "1234")
  public String password;
}

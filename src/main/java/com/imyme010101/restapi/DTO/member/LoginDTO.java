package com.imyme010101.restapi.DTO.member;

import org.springframework.boot.context.properties.bind.Name;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
  @NotEmpty
  @Pattern(regexp = "^[a-z0-9]{4,}$")
  @Parameter(description = "영문 숫자 조합", example = "abcd1234")
  private String id;

  @NotEmpty
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
  @Parameter(description = "영문, 특수문자 8자 이상 20자 이하", example = "abcd12#$12#$")
  private String password;
}

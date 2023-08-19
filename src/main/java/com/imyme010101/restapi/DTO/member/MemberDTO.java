package com.imyme010101.restapi.DTO.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;


public class MemberDTO {
  @NotEmpty
  @Pattern(regexp = "^[a-zA-Z0-9]$")
  @Schema(description = "영문 숫자 조합", defaultValue = "abcd1234")
  public String id;

  @NotEmpty
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
  @Schema(description = "영문, 특수문자 8자 이상 20자 이하", defaultValue = "abcd12#$12#$")
  public String password;

  @NotEmpty
  @Schema(description = "role 테이블 참조", defaultValue = "U")
  public String roles;

  @NotEmpty
  @Schema(description = "이름", defaultValue = "홍길동")
  public String name;

  @Schema(description = "생년월일")
  public LocalDate dateOfBirth;

  @NotEmpty
  @Pattern(regexp = "^[0-9]$")
  @Schema(description = "숫자 만 허용", defaultValue = "01012345678")
  public String phoneNumber;

  @NotEmpty
  @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
  @Schema(description = "이메일 주소", defaultValue = "example@example.com")
  public String email;

  @Schema(description = "우편번호", defaultValue = "12345")
  public String zipCode;

  @Schema(description = "주소", defaultValue = "123 Main St")
  public String address;

  @Schema(description = "성별", defaultValue = "UNKNOWN")
  public Gender gender;

  @Schema(description = "상태", defaultValue = "ENABLE")
  public Status status;

  public LocalDateTime deleteAt;

  public LocalDateTime createdAt;

  public enum Gender {
    UNKNOWN, MALE, FEMALE
  }

  public enum Status {
    ENABLE, DISABLE
  }
}

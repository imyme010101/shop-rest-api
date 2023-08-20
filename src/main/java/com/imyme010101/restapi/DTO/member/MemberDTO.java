package com.imyme010101.restapi.DTO.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
  @Parameter(name = "id", description = "영문 숫자 조합, 4자 이상", example = "abcd1234")
  @NotEmpty
  @Pattern(regexp = "^[a-z0-9]{4,}$")
  private String id;
  
  @Parameter(name = "password", description = "영문, 특수문자 8자 이상 20자 이하", example = "abcd12#$12#$")
  @NotEmpty
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$")
  private String password;
  
  @Parameter(name = "roles", description = "role 테이블 참조", example = "U")
  @NotEmpty
  private String roles;
  
  @Parameter(name = "name", description = "이름", example = "홍길동")
  @NotEmpty
  private String name;
  
  @Schema(name = "date_of_birth", description = "생년월일")
  private LocalDate dateOfBirth;
  
  @Parameter(name = "phone_number", description = "숫자만 허용", example = "01012345678")
  @Pattern(regexp = "^[0-9]+$")
  private String phoneNumber;
  
  @Parameter(name = "email", description = "이메일 주소", example = "example@example.com")
  @NotEmpty
  @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
  private String email;
  
  @Parameter(name = "zip_code", description = "우편번호", example = "12345")
  private String zipCode;
  
  @Parameter(name = "address", description = "주소", example = "123 Main St")
  private String address;
  
  @Parameter(description = "성별", example = "UNKNOWN")
  private Gender gender;
  
  @Parameter(description = "상태", example = "ENABLE")
  private Status status;
  
  private LocalDateTime deleteAt;
  
  private LocalDateTime createdAt;

  private enum Gender {
    UNKNOWN, MALE, FEMALE
  }

  private enum Status {
    ENABLE, DISABLE
  }
}

package com.imyme010101.restapi.DTO.member;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class SignupDTO {
  @NotEmpty
  @Pattern(regexp = "^[a-zA-Z0-9]$")
  public String id;
  @NotEmpty
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$") // 영문, 특수문자 8자 이상 20자 이하
  public String password;
  public String roles;
  public String name;
  public LocalDate dateOfBirth;
  public String phoneNumber;
  public String email;
  public String zipCode;
  public String address;
  public Gender gender;

  public enum Gender {
    M, F
  }

  public enum Status {
    Y, N
  }
}

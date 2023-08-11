package com.imyme010101.restapi.DTO.member;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class MembersDTO {
  public String id;
  public String password;
  public String roles;
  public String name;
  public LocalDate dateOfBirth;
  public String phoneNumber;
  public String email;
  public String zipCode;
  public String address;
  public Gender gender;
  public Status status;
  public LocalDateTime deleteAt;
  public LocalDateTime createdAt;

  public enum Gender {
    M, F
  }

  public enum Status {
    Y, N
  }
}

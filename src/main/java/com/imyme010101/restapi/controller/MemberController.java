package com.imyme010101.restapi.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imyme010101.restapi.common.response.ApiResponse;
import com.imyme010101.restapi.config.jwt.JwtMemberService;
import com.imyme010101.restapi.service.MemberService;
import com.imyme010101.restapi.DTO.TokenDTO;
import com.imyme010101.restapi.DTO.member.LoginDTO;
import com.imyme010101.restapi.DTO.member.MemberDTO;
import com.imyme010101.restapi.util.EncryptionUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Tag(name = "회원 관리", description = "로그인/회원가입/로그아웃")
@Validated
@RestController
@RequestMapping("/member")
public class MemberController {
  private int status;
  private String message = "";
  private Object data = null;

  @Autowired
  private JwtMemberService jwtMemberService;
  @Autowired
  private MemberService memberService;

  @Operation(summary = "회원 가입", description = "member 테이블 추가, 회원가입")
  @PutMapping("/register")
  public ResponseEntity<ApiResponse> signup(@ParameterObject @Valid MemberDTO memberDTO) throws Exception {
    memberDTO.setPassword(EncryptionUtil.encrypt(memberDTO.getPassword()));

    if(memberService.add(memberDTO)) {
      this.status = 200;
      this.message = "정상적으로 처리 되었습니다.";
      this.data = memberDTO;
    } else {
      this.status = 201;
      this.message = "비정상적으로 처리 되었습니다.";
      this.data = memberDTO;
    }

    return ResponseEntity.badRequest().body(ApiResponse.builder()
        .status(this.status)
        .resultMsg(this.message)
        .result(this.data)
        .build());
  }

  @Operation(summary = "로그인 토큰 발급", description = "모든 API 호출을 하기 위해서 토큰은 발급")
  @PostMapping("/auth")
  public ResponseEntity<ApiResponse> auth(@ParameterObject @Valid LoginDTO loginDTO) throws Exception {
    TokenDTO tokenDTO = jwtMemberService.auth(loginDTO.getId(), EncryptionUtil.encrypt(loginDTO.getPassword()));

    return ResponseEntity.badRequest().body(ApiResponse.builder()
        .status(200)
        .resultMsg("정상적으로 처리 되었습니다.")
        .result(tokenDTO)
        .build());
  }

  @Operation(summary = "아이디 중복 체크")
  @GetMapping("/check/id")
  public ResponseEntity<ApiResponse> checkId(@RequestParam("id") @Valid @Pattern(regexp = "^[a-z0-9]{4,}$", message = "영문 숫자 조합, 4자 이상") String id) {

    if (memberService.check("id", id)) {
      this.status = 200;
      this.message = "사용 하실수 있는 아이디 입니다.";
      this.data = null;
    } else {
      this.status = 201;
      this.message = "사용 하실수 없는 아이디 입니다.";
      this.data = null;
    }

    return ResponseEntity.badRequest().body(ApiResponse.builder()
        .status(this.status)
        .resultMsg(this.message)
        .result(this.data)
        .build());
  }

  @Operation(summary = "이메일 중복 체크")
  @GetMapping("/check/email")
  public ResponseEntity<ApiResponse> checkEmail(@RequestParam("email") @Valid @Pattern(regexp = "^[a-z0-9]{4,}@([a-z0-9]{2,}\\.){1,}[a-z]{2,3}$", message = "example@domain.com") String email) {
    if (memberService.check("email", email)) {
      this.status = 200;
      this.message = "사용 하실수 있는 이메일 입니다.";
      this.data = null;
    } else {
      this.status = 201;
      this.message = "사용 하실수 없는 이메일 입니다.";
      this.data = null;
    }

    return ResponseEntity.badRequest().body(ApiResponse.builder()
        .status(this.status)
        .resultMsg(this.message)
        .result(this.data)
        .build());
  }
}

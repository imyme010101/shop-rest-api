package com.imyme010101.restapi.controller;

import java.util.HashMap;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.Param;

import com.imyme010101.restapi.DTO.ResultDTO;
import com.imyme010101.restapi.DTO.TokenDTO;
import com.imyme010101.restapi.DTO.member.LoginDTO;
import com.imyme010101.restapi.DTO.member.MemberDTO;
import com.imyme010101.restapi.service.security.MemberService;
import com.imyme010101.restapi.util.EncryptionUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Tag(name = "회원 관리", description = "로그인/회원가입/로그아웃")
@Validated
@RestController
@RequestMapping("/member")
public class MemberController {
  private String code = "";
  private String message = "";
  private Object data = null;

  @Autowired
  private MemberService memberService;

  @Operation(summary = "회원 가입", description = "member 테이블 추가, 회원가입")
  @PutMapping("/signup")
  public ResponseEntity<ResultDTO> signup(@ParameterObject @Valid MemberDTO memberDTO) {
    try {
      memberDTO.setPassword(EncryptionUtil.encrypt(memberDTO.getPassword()));

      memberService.signup(memberDTO);
      this.code = "SUCCESS";
      this.message = "정상적으로 처리 되었습니다.";
      this.data = memberDTO;
    } catch (Exception e) {
      this.code = "FAIL";
      this.message = e.getMessage();
      this.data = null;
    }

    return ResponseEntity.badRequest().body(ResultDTO.builder()
        .code(this.code)
        .message(this.message)
        .data(this.data)
        .build());
  }

  @Operation(summary = "로그인 토큰 발급", description = "모든 API 호출을 하기 위해서 토큰은 발급")
  @PostMapping("/signin")
  public ResponseEntity<ResultDTO> signin(@ParameterObject @Valid LoginDTO loginDTO) throws Exception {
    TokenDTO tokenDTO = memberService.signin(loginDTO.getId(), EncryptionUtil.encrypt(loginDTO.getPassword()));

    this.code = "SUCCESS";
    this.message = "정상적으로 처리 되었습니다.";
    this.data = tokenDTO;
    
    return ResponseEntity.badRequest().body(ResultDTO.builder()
        .code(this.code)
        .message(this.message)
        .data(this.data)
        .build());
  }

  @Operation(summary = "아이디 중복 체크")
  @GetMapping("/check/id")
  public ResponseEntity<ResultDTO> checkId(
      @RequestParam("id") @Valid @Pattern(regexp = "^[a-z0-9]{4,}$", message = "영문 숫자 조합, 4자 이상") String id) {
    return null;
  }
}

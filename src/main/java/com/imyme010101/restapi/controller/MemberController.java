package com.imyme010101.restapi.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imyme010101.restapi.DTO.ResultDTO;
import com.imyme010101.restapi.DTO.TokenDTO;
import com.imyme010101.restapi.DTO.member.LoginDTO;
import com.imyme010101.restapi.DTO.member.MemberDTO;
import com.imyme010101.restapi.service.security.MemberService;
import com.imyme010101.restapi.util.EncryptionUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원 관리", description = "로그인/회원가입/로그아웃")
@RestController
@Validated
@RequestMapping("/member")
public class MemberController {
  private String code = "";
  private String message = "";
  private Object data = null;

  @Autowired
  private MemberService memberService;

  @Operation(summary = "회원 가입", description = "member 테이블 추가, 회원가입")
  @PostMapping("/signup")
  public ResponseEntity<ResultDTO> signup(@RequestBody @Validated MemberDTO memberDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      HashMap<String, String> errors = new HashMap<>();

      bindingResult.getFieldErrors().forEach(error -> {
        errors.put(error.getField(), error.getDefaultMessage());
      });

      this.code = "FAIL";
      this.message = "입력값이 틀렸습니다.";
      this.data = errors;
    } else {
      try {
        memberDTO.password = EncryptionUtil.encrypt(memberDTO.password);
        
        memberService.signup(memberDTO);
        this.code = "SUCCESS";
        this.message = "정상적으로 처리 되었습니다.";
        this.data = memberDTO;
      } catch (Exception e) {
        this.code = "FAIL";
        this.message = e.getMessage();
        this.data = null;
      }
    }

    return ResponseEntity.badRequest().body(ResultDTO.builder()
        .code(this.code)
        .message(this.message)
        .data(this.data)
        .build());
  }

  @Operation(summary = "로그인 토큰 발급", description = "모든 API 호출을 하기 위해서 토큰은 발급")
  @PostMapping("/signin")
  public ResponseEntity<ResultDTO> signin(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      HashMap<String, String> errors = new HashMap<>();

      bindingResult.getFieldErrors().forEach(error -> {
        errors.put(error.getField(), error.getDefaultMessage());
      });

      this.code = "FAIL";
      this.message = "입력값이 틀렸습니다.";
      this.data = errors;
    } else {
      try {
        String memberId = loginDTO.id;
        String password = EncryptionUtil.encrypt(loginDTO.password);
        
        TokenDTO tokenDTO = memberService.signin(memberId, password);

        if (tokenDTO.accessToken != null) {
          this.code = "SUCCESS";
          this.message = "정상적으로 처리 되었습니다.";
          this.data = tokenDTO;
        } else {
          this.code = "FAIL";
          this.message = "회원 정보가 존재 않습니다.";
          this.data = null;
        }
      } catch (Exception e) {
        this.code = "FAIL";
        this.message = e.getMessage();
        this.data = null;
      }
    }

    return ResponseEntity.badRequest().body(ResultDTO.builder()
        .code(this.code)
        .message(this.message)
        .data(this.data)
        .build());
  }
}

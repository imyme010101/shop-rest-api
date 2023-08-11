package com.imyme010101.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imyme010101.restapi.DTO.TokenDTO;
import com.imyme010101.restapi.DTO.member.LoginDTO;
import com.imyme010101.restapi.service.security.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "회원 관리", description = "로그인/회원가입/로그아웃")
@RestController
@Validated
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;


    // @PostMapping("/signup")
    // public ResponseEntity signup(@RequestBody @Valid SignupDTO signupDTO, BindingResult bindingResult) throws Exception {
    //     if (bindingResult.hasErrors()) {
    //         return new ResponseEntity(HttpStatus.BAD_REQUEST);
    //     }

    //     SignupDTO signup = new SignupDTO();
    //     signup.id = signupDTO.id;
    //     signup.password = passwordEncoder.encode(signupDTO.password);
    //     signup.name = signupDTO.name;
    //     signup.dateOfBirth = signupDTO.dateOfBirth;
    //     signup.phoneNumber = signupDTO.phoneNumber;
    //     signup.email = signupDTO.email;
    //     signup.zipCode = signupDTO.zipCode;
    //     signup.address = signupDTO.address;
    //     signup.gender = signupDTO.gender;

    //     HttpStatus status;
    //     if(memberRepository.addMember(signup) != 0) {
    //         signup.password = signupDTO.password;
    //         status = HttpStatus.CREATED;
    //     } else {        
    //         signup.password = signupDTO.password;
    //         status = HttpStatus.BAD_REQUEST;
    //     }
        
    //     return new ResponseEntity(signup, status);
    // }

    @Operation(summary = "로그인 토큰 발급", description = "모든 API 호출을 하기 위해서 토큰은 발급")
    // @Parameters(
    //     value = {
    //         @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "로그인 ID", required = true),
    //         @io.swagger.v3.oas.annotations.Parameter(name = "password", description = "로그인 PASSWORD", required = true)
    //     }
    // )
    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO) {
        String memberId = loginDTO.id;
        String password = loginDTO.password;
        TokenDTO tokenDTO = memberService.login(memberId, password);
        
        return tokenDTO;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}

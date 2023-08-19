package com.imyme010101.restapi.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imyme010101.restapi.DTO.TokenDTO;
import com.imyme010101.restapi.DTO.member.MemberDTO;
import com.imyme010101.restapi.jwt.JwtTokenProvider;
import com.imyme010101.restapi.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {
  @Autowired
  private AuthenticationManagerBuilder authenticationManagerBuilder;
  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Autowired
  private MemberRepository memberRepository;

  @Transactional
  public TokenDTO signin(String memberId, String password) {
    // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
    // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

    // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
    // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername
    // 메서드가 실행
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    
    // 3. 인증 정보를 기반으로 JWT 토큰 생성
    TokenDTO tokenDTO = jwtTokenProvider.generateToken(authentication);

    return tokenDTO;
  }

  @Transactional
  public boolean signup(MemberDTO memberDTO) throws Exception {
    if(memberRepository.add(memberDTO) == 0) {
      return false;
    } else {
      return true;
    }
  }

  public MemberDTO info(String memberId) {
    return memberRepository.findAll("id", memberId);
  }
}
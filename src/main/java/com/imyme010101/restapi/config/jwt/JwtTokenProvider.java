package com.imyme010101.restapi.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Arrays;

import com.imyme010101.restapi.DTO.TokenDTO;
import com.imyme010101.restapi.util.SecurityUtil;

@Component
public class JwtTokenProvider {
   private Key key;
   private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

   public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
      byte[] keyBytes = Decoders.BASE64.decode(secretKey);
      this.key = Keys.hmacShaKeyFor(keyBytes);
   }

   // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
   public TokenDTO generateToken(Authentication authentication) {
      // 권한 가져오기
      String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

      long now = (new Date()).getTime();
      // Access Token 생성
      Date accessTokenExpiresIn = new Date(now + 60);
      String accessToken = Jwts.builder()
            .setSubject(authentication.getName())
            .claim("auth", authorities)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

      // Refresh Token 생성
      String refreshToken = Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

      TokenDTO tokenDTO = new TokenDTO();
      tokenDTO.grantType = "Bearer";
      tokenDTO.accessToken = accessToken;
      tokenDTO.refreshToken = refreshToken;

      return tokenDTO;
   }

   // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
   public Authentication getAuthentication(String accessToken) {
      // 토큰 복호화
      Claims claims = parseClaims(accessToken);

      if (claims.get("auth") == null) {
         throw new RuntimeException("권한 정보가 없는 토큰입니다.");
      }

      // 클레임에서 권한 정보 가져오기
      Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

      // UserDetails 객체를 만들어서 Authentication 리턴
      UserDetails principal = new User(claims.getSubject(), "", authorities);
      return new UsernamePasswordAuthenticationToken(principal, "", authorities);
   }

   // 토큰 정보를 검증하는 메서드
   public boolean validateToken(String token) {
      try {
         Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
         return true;
      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         logger.debug("Invalid JWT Token", e);
      } catch (ExpiredJwtException e) {
         logger.debug("Expired JWT Token", e);
      } catch (UnsupportedJwtException e) {
         logger.debug("Unsupported JWT Token", e);
      } catch (IllegalArgumentException e) {
         logger.debug("JWT claims string is empty.", e);
      }
      return false;
   }

   private Claims parseClaims(String accessToken) {
      try {
         return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
      } catch (ExpiredJwtException e) {
         return e.getClaims();
      }
   }
}
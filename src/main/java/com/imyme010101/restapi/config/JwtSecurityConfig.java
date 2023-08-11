package com.imyme010101.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.imyme010101.restapi.jwt.JwtFilter;
import com.imyme010101.restapi.jwt.JwtTokenProvider;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(
        new JwtFilter(jwtTokenProvider),
        UsernamePasswordAuthenticationFilter.class
    );
  }
}
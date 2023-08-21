package com.imyme010101.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.imyme010101.restapi.config.jwt.JwtFilter;
import com.imyme010101.restapi.config.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable);

		http
				.sessionManagement(
					session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.authorizeHttpRequests(
					authorize -> authorize
						.requestMatchers("/member/signin").permitAll() // 로그인 api
						.requestMatchers("/member/signup").permitAll() // 회원가입 api
						.requestMatchers("/member/check/**").permitAll() // 회원가입 api
						.requestMatchers("/favicon.ico").permitAll()
						.requestMatchers(
							"/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui/**", "/swagger/**",   // swagger
							"/favicon.ico"
						).permitAll()
						.anyRequest().authenticated() // 그 외 인증 없이 접근X
				)
				.addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
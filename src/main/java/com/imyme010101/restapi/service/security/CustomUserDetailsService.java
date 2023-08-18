package com.imyme010101.restapi.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.imyme010101.restapi.DTO.member.MemberDTO;
import com.imyme010101.restapi.repository.MemberRepository;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        MemberDTO member = memberRepository.findById(username);
        if (member != null) {
            return createUserDetails(member);
        } else {
            throw new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다.");
        }
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(MemberDTO member) {
        return User.builder()
                .username(member.id)
                .password(passwordEncoder.encode(member.password))
                .roles(member.roles.split(","))
                .build();
    }
}
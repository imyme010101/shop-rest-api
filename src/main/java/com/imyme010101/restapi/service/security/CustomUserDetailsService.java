package com.imyme010101.restapi.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.imyme010101.restapi.DTO.member.MembersDTO;
import com.imyme010101.restapi.repository.MembersRepository;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        MembersDTO member = membersRepository.findById(username);
        if (member != null) {
            return createUserDetails(member);
        }
        return null;
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(MembersDTO member) {
        return User.builder()
                .username(member.id)
                .password(passwordEncoder.encode(member.password))
                .roles(member.roles.toArray(new String[0]))
                .build();
    }
}
package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.MemberDto;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MyCustomMemberDetailsService implements UserDetailsService {

    private final MemberService memberService;

    public MyCustomMemberDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        MemberDto memberDto = memberService.getMember(email);
        String password = memberDto.getPassword();

        Set<Authority> authorities = memberDto.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        return new org.springframework.security.core.userdetails.User(email, password, grantedAuthorities);
    }
}

package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.User;
import com.novi.gymmanagementapi.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyCustomMemberDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyCustomMemberDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        Optional<User> optionalUser = userRepository.findById(email);

        String password = null;
        Set<Authority> authorities = new HashSet<>();

        if (optionalUser.isPresent()) {
            password = optionalUser.get().getPassword();
            authorities = optionalUser.get().getAuthorities();
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(email, password, grantedAuthorities);
    }
}

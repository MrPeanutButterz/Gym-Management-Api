package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.models.Admin;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Trainer;
import com.novi.gymmanagementapi.repositories.AdminRepository;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.TrainerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyCustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final AdminRepository adminRepository;

    public MyCustomMemberDetailsService(MemberRepository memberRepository,
                                        TrainerRepository trainerRepository,
                                        AdminRepository adminRepository) {
        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        Optional<Member> optionalMember = memberRepository.findById(email);
        Optional<Trainer> optionalTrainer = trainerRepository.findById(email);
        Optional<Admin> optionalAdmin = adminRepository.findById(email);

        String password = null;
        Set<Authority> authorities = new HashSet<>();

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            password = member.getPassword();
            authorities = member.getAuthorities();

        } else if (optionalTrainer.isPresent()) {
            Trainer trainer = optionalTrainer.get();
            password = trainer.getPassword();
            authorities = trainer.getAuthorities();

        } else if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            password = admin.getPassword();
            authorities = admin.getAuthorities();
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        if (authorities.size() > 0) {
            return new org.springframework.security.core.userdetails.User(email, password, grantedAuthorities);

        } else {
            throw new EmailNotFoundException();
        }
    }
}

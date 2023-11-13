package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.MemberDto;
import com.novi.gymmanagementapi.dto.NewUser;
import com.novi.gymmanagementapi.exceptions.EmailAlreadyTakenException;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDto createMember(NewUser dto) {
        Optional<Member> optionalMember = memberRepository.findById(dto.getEmail());
        if (optionalMember.isEmpty()) {
            Member model = new Member();
            model.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
            model.setEmail(dto.getEmail());
            model.setFirstname(dto.getFirstname());
            model.setLastname(dto.getLastname());
            model.setDateOfBirth(dto.getDateOfBirth());
            model = memberRepository.save(model);
            addAuthority(model.getEmail(), "ROLE_MEMBER");
            return asDTO(model);

        } else {
            throw new EmailAlreadyTakenException(dto.getEmail());
        }
    }

    public MemberDto getMember(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            return asDTO(optionalMember.get());

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public MemberDto updateMember(Principal principal, MemberDto dto) {
        Optional<Member> optionalMember = memberRepository.findById(principal.getName());
        if (optionalMember.isPresent()) {
            Member model = asMODEL(dto);
            model.setEnabled(optionalMember.get().isEnabled());
            model.setAuthorities(optionalMember.get().getAuthorities());
            memberRepository.save(model);
            memberRepository.delete(optionalMember.get());
            // todo move authorities for old account to new
            return asDTO(model);

        } else {
            throw new EmailNotFoundException(dto.getEmail());
        }
    }

    public void deleteMember(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member model = optionalMember.get();
            model.setEnabled(false);
            memberRepository.save(model);
            removeAuthority(email, "ROLE_MEMBER");
        }
    }

    public Set<Authority> getAuthorities(String username) {
        if (!memberRepository.existsById(username)) throw new UsernameNotFoundException(username);
        Member member = memberRepository.findById(username).get();
        MemberDto memberDto = asDTO(member);
        return memberDto.getAuthorities();
    }

    public void removeAuthority(String username, String authority) {
        if (!memberRepository.existsById(username)) throw new UsernameNotFoundException(username);
        Member member = memberRepository.findById(username).get();
        Authority authorityToRemove = member.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        member.removeAuthority(authorityToRemove);
        memberRepository.save(member);
    }

    public void addAuthority(String email, String authority) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member model = optionalMember.get();
            model.addAuthority(new Authority(email, authority));
            memberRepository.save(model);

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public MemberDto asDTO(Member model) {
        MemberDto dto = new MemberDto();
        dto.setEmail(model.getEmail());
        dto.setPassword(model.getPassword());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        dto.setMembership(model.getMembership());
        dto.setEnabled(model.isEnabled());
        dto.setAuthorities(model.getAuthorities());
        return dto;
    }

    public Member asMODEL(MemberDto dto) {
        Member model = new Member();
        model.setEmail(dto.getEmail());
        model.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        model.setFirstname(dto.getFirstname());
        model.setLastname(dto.getLastname());
        model.setDateOfBirth(dto.getDateOfBirth());
        model.setEnabled(true);
        return model;
    }
}
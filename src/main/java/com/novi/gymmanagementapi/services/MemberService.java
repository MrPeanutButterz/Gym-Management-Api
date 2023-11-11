package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dtobject.MemberDto;
import com.novi.gymmanagementapi.exceptions.EmailAlreadyTakenException;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.exceptions.RecordNotFoundException;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String createMember(MemberDto dto) {
        Optional<Member> optionalMember = memberRepository.findById(dto.email);
        if (optionalMember.isEmpty()) {
            Member model = memberRepository.save(toMODEL(dto));
            return model.getEmail();

        } else {
            throw new EmailAlreadyTakenException(dto.email);
        }
    }

    public MemberDto getMember(String email) {
        Optional<Member> om = memberRepository.findById(email);
        if (om.isPresent()) {
            MemberDto dto = toDTO(om.get());
            // todo set goal IDs
            return dto;

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    /* WORKING FUNCTIONS ABOVE ^ */

    public List<MemberDto> getMembers() {
        List<MemberDto> collection = new ArrayList<>();
        List<Member> list = memberRepository.findAll();
        for (Member member : list) {
            collection.add(toDTO(member));
        }
        return collection;
    }
/*
    public MemberDto getMember(String email) {
        Optional<Member> user = memberRepository.findById(email);
        if (user.isPresent()) {
            return toDTO(user.get());
        } else {
            throw new EmailNotFoundException(email);
        }
    }*/

    public boolean userExists(String email) {
        return memberRepository.existsById(email);
    }

    public void deleteMember(String username) {
        memberRepository.deleteById(username);
    }

    public void updateMember(String username, MemberDto newMember) {
        if (!memberRepository.existsById(username)) throw new RecordNotFoundException();
        Member member = memberRepository.findById(username).get();
        member.setPassword(newMember.getPassword());
        memberRepository.save(member);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!memberRepository.existsById(username)) throw new UsernameNotFoundException(username);
        Member member = memberRepository.findById(username).get();
        MemberDto memberDto = toDTO(member);
        return memberDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!memberRepository.existsById(username)) throw new UsernameNotFoundException(username);
        Member member = memberRepository.findById(username).get();
        member.addAuthority(new Authority(username, authority));
        memberRepository.save(member);
    }

    public void removeAuthority(String username, String authority) {
        if (!memberRepository.existsById(username)) throw new UsernameNotFoundException(username);
        Member member = memberRepository.findById(username).get();
        Authority authorityToRemove = member.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        member.removeAuthority(authorityToRemove);
        memberRepository.save(member);
    }

    public MemberDto toDTO(Member model) {
        var dto = new MemberDto();
        dto.setPassword(model.getPassword());
        dto.setEnabled(model.isEnabled());
        dto.setEmail(model.getEmail());
        dto.setAuthorities(model.getAuthorities());
        return dto;
    }

    public Member toMODEL(MemberDto dto) {
        var user = new Member();
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setEnabled(dto.getEnabled());
        user.setEmail(dto.getEmail());
        return user;
    }
}

package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dtobject.MemberDto;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.exceptions.RecordNotFoundException;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public List<MemberDto> getUsers() {
        List<MemberDto> collection = new ArrayList<>();
        List<Member> list = memberRepository.findAll();
        for (Member member : list) {
            collection.add(fromUser(member));
        }
        return collection;
    }

    public MemberDto getUser(String email) {
        Optional<Member> user = memberRepository.findUserByEmail(email);
        if (user.isPresent()) {
            return fromUser(user.get());
        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public boolean userExists(String username) {
        return memberRepository.existsById(username);
    }

    public String createUser(MemberDto memberDto) {
        Member newMember = memberRepository.save(toUser(memberDto));
        return newMember.getEmail();
    }

    public void deleteUser(String username) {
        memberRepository.deleteById(username);
    }

    public void updateUser(String username, MemberDto newUser) {
        if (!memberRepository.existsById(username)) throw new RecordNotFoundException();
        Member member = memberRepository.findById(username).get();
        member.setPassword(newUser.getPassword());
        memberRepository.save(member);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!memberRepository.existsById(username)) throw new UsernameNotFoundException(username);
        Member member = memberRepository.findById(username).get();
        MemberDto memberDto = fromUser(member);
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

    public MemberDto fromUser(Member model) {
        var dto = new MemberDto();
        dto.setPassword(model.getPassword());
        dto.setEnabled(model.isEnabled());
        dto.setEmail(model.getEmail());
        dto.setAuthorities(model.getAuthorities());
        return dto;
    }

    public Member toUser(MemberDto dto) {
        var user = new Member();
        user.setPassword(dto.getPassword());
        user.setEnabled(dto.getEnabled());
        user.setEmail(dto.getEmail());
        return user;
    }
}

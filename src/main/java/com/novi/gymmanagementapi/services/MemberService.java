package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.*;
import com.novi.gymmanagementapi.exceptions.EmailAlreadyTakenException;
import com.novi.gymmanagementapi.exceptions.EmailNotFoundException;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Trainer;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public UserDto createMember(MemberDto dto) {
        Optional<Member> optionalMember = memberRepository.findById(dto.getEmail());
        if (optionalMember.isEmpty()) {
            Member member = asMODEL(dto);
            member = memberRepository.save(member);
            addAuthority(member.getEmail(), "ROLE_MEMBER");
            return asDTO(member);

        } else {
            throw new EmailAlreadyTakenException(dto.getEmail());
        }
    }

    public MemberResponseDto getMemberAccount(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            return asDTO(optionalMember.get());

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public MemberResponseDto updateMember(String email, MemberDto dto) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
            member.setFirstname(dto.getFirstname());
            member.setLastname(dto.getLastname());
            member.setDateOfBirth(dto.getDateOfBirth());
            memberRepository.save(member);
            return asDTO(member);

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

    public void removeAuthority(String email, String authority) {
        if (!memberRepository.existsById(email)) throw new UsernameNotFoundException(email);
        Member member = memberRepository.findById(email).get();
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

    private TrainerResponseDto modelToResponse(Trainer model) {
        TrainerResponseDto dto = new TrainerResponseDto();
        dto.setEmail(model.getEmail());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        return dto;
    }

    private MemberResponseDto asDTO(Member model) {
        MemberResponseDto dto = new MemberResponseDto();
        dto.setEmail(model.getEmail());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        if (model.getMembership() != null) { dto.setMembership(model.getMembership()); }
        if (model.getTrainer() != null) { dto.setTrainer(modelToResponse(model.getTrainer())); }
        if (model.getGoals() != null) { dto.setGoalIDs(model.getGoalIDs()); }
        return dto;
    }

    private Member asMODEL(MemberDto dto) {
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
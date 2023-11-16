package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.FullMemberDto;
import com.novi.gymmanagementapi.dto.PartialMemberDto;
import com.novi.gymmanagementapi.dto.PartialTrainerDto;
import com.novi.gymmanagementapi.dto.UserDto;
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
import java.util.Set;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public UserDto createMember(FullMemberDto dto) {
        Optional<Member> optionalMember = memberRepository.findById(dto.getEmail());
        if (optionalMember.isEmpty()) {
            Member member = new Member();
            member.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
            member.setEmail(dto.getEmail());
            member.setFirstname(dto.getFirstname());
            member.setLastname(dto.getLastname());
            member.setDateOfBirth(dto.getDateOfBirth());
            member = memberRepository.save(member);
            addAuthority(member.getEmail(), "ROLE_MEMBER");
            return asDTO(member);

        } else {
            throw new EmailAlreadyTakenException(dto.getEmail());
        }
    }

    public PartialMemberDto getMemberAccount(String email) {
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            return asDTO(optionalMember.get());

        } else {
            throw new EmailNotFoundException(email);
        }
    }

    public PartialMemberDto updateMember(String email, FullMemberDto dto) {
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

    public Set<Authority> getAuthorities(String username) {
        // todo this function is not working yet
        if (!memberRepository.existsById(username)) throw new UsernameNotFoundException(username);
        Member member = memberRepository.findById(username).get();
        //FullMemberDto fullMemberDto = asDTO(member);
        //return memberDto.getAuthorities();
        return null;
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

    private PartialMemberDto asDTO(Member model) {
        PartialMemberDto dto = new PartialMemberDto();
        dto.setEmail(model.getEmail());
        dto.setFirstname(model.getFirstname());
        dto.setLastname(model.getLastname());
        dto.setDateOfBirth(model.getDateOfBirth());
        dto.setMembership(model.getMembership());
        dto.setGoalIDs(model.getGoalIDs());
        Trainer trainer = model.getTrainer();
        if (trainer != null) {
            PartialTrainerDto pt = new PartialTrainerDto();
            pt.setEmail(trainer.getEmail());
            pt.setFirstname(trainer.getFirstname());
            pt.setLastname(trainer.getLastname());
            pt.setDateOfBirth(trainer.getDateOfBirth());
            pt.setHourlyRate(trainer.getHourlyRate());
            dto.setTrainer(pt);
        }
        return dto;
    }

    private Member asMODEL(FullMemberDto dto) {
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
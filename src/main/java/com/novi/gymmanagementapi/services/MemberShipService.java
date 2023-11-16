package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.MembershipDto;
import com.novi.gymmanagementapi.exceptions.RecordNotFoundException;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Membership;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.MembershipRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberShipService {

    private final MembershipRepository memberShipRepository;
    private final MemberRepository memberRepository;

    public MemberShipService(MembershipRepository memberShipRepository,
                             MemberRepository memberRepository) {
        this.memberShipRepository = memberShipRepository;
        this.memberRepository = memberRepository;
    }

    public MembershipDto createMembership(MembershipDto membershipDto) {
        Membership membership = memberShipRepository.save(asMODEL(membershipDto));
        return asDTO(membership);
    }

    public List<MembershipDto> getMemberships() {
        List<Membership> membershipList = new ArrayList<>(memberShipRepository.findAll());
        List<MembershipDto> membershipDtoList = new ArrayList<>();
        for (Membership membership : membershipList) {
            MembershipDto membershipDto = asDTO(membership);
            membershipDto.setId(membership.getId());
            membershipDtoList.add(membershipDto);
        }
        return membershipDtoList;
    }

    public MembershipDto updateMembership(long membershipID, MembershipDto membershipDto) {
        Optional<Membership> optionalMembership = memberShipRepository.findById(membershipID);
        if (optionalMembership.isPresent()) {
            Membership membership = optionalMembership.get();
            membership.setName(membershipDto.getName());
            membership.setPricePerMonth(membershipDto.getPricePerMonth());
            membership.setContractLengthInWeek(membershipDto.getContractLengthInWeek());
            memberShipRepository.save(membership);
            return asDTO(membership);

        } else {
            throw new RecordNotFoundException(membershipID);
        }
    }

    public void deleteMembership(long membershipID) {
        Optional<Membership> optionalMembership = memberShipRepository.findById(membershipID);
        if (optionalMembership.isPresent()) {
            memberShipRepository.deleteById(membershipID);

        } else {
            throw new RecordNotFoundException(membershipID);
        }
    }

    public MembershipDto subscribe(long membershipID, String email) {
        Optional<Membership> optionalMembership = memberShipRepository.findById(membershipID);
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMembership.isPresent() && optionalMember.isPresent()) {
            Membership membership = optionalMembership.get();
            Member member = optionalMember.get();
            member.setMembership(membership);
            memberRepository.save(member);
            return asDTO(membership);

        } else {
            throw new RecordNotFoundException(membershipID);
        }
    }

    public void unsubscribe(String email) {

        // todo make this function with expiration date
        Optional<Member> optionalMember = memberRepository.findById(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setMembership(null);
            memberRepository.save(member);

        } else {
            throw new RecordNotFoundException();
        }
    }

    private MembershipDto asDTO(Membership model) {
        MembershipDto dto = new MembershipDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setContractLengthInWeek(model.getContractLengthInWeek());
        dto.setPricePerMonth(model.getPricePerMonth());
        return dto;
    }

    private Membership asMODEL(MembershipDto dto) {
        Membership memberShip = new Membership();
        memberShip.setName(dto.getName());
        memberShip.setContractLengthInWeek(dto.getContractLengthInWeek());
        memberShip.setPricePerMonth(dto.getPricePerMonth());
        return memberShip;
    }
}

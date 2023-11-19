package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.MembershipDto;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Membership;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.MembershipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class MembershipServiceTest {

    @Mock
    MembershipRepository membershipRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    private MembershipService membershipService;

    Membership testMembershipOne = new Membership();
    Membership testMembershipTwo = new Membership();
    MembershipDto testMembershipDtoOne = new MembershipDto();
    MembershipDto testMembershipDtoTwo = new MembershipDto();
    Member memberOne = new Member();

    @BeforeEach
    void setUp() {
        testMembershipOne.setName("MembershipOne");
        testMembershipOne.setPricePerMonth(50);
        testMembershipOne.setContractLengthInWeek(100);

        testMembershipDtoOne.setName("MembershipDtoOne");
        testMembershipDtoOne.setPricePerMonth(51);
        testMembershipDtoOne.setContractLengthInWeek(101);

        testMembershipTwo.setId(1L);
        testMembershipTwo.setName("MembershipTwo");
        testMembershipTwo.setPricePerMonth(52);
        testMembershipTwo.setContractLengthInWeek(102);

        testMembershipDtoTwo.setId(1L);
        testMembershipDtoTwo.setName("MembershipTwo");
        testMembershipDtoTwo.setPricePerMonth(52);
        testMembershipDtoTwo.setContractLengthInWeek(102);

        memberOne.setEmail("test@mail.com");
        memberOne.setFirstname("Jack");
        memberOne.setLastname("Daniels");
        memberOne.setDateOfBirth(LocalDate.parse("2000-01-01"));
        memberOne.setEnabled(true);

    }

    @Test
    @DisplayName("Should create a new membership")
    void shouldCreateMembership() {

        MembershipDto inputMembershipDto = new MembershipDto();
        inputMembershipDto.setName("MembershipOne");
        inputMembershipDto.setPricePerMonth(50);
        inputMembershipDto.setContractLengthInWeek(100);

        Membership savedMembership = new Membership();
        savedMembership.setId(2L);
        savedMembership.setName(inputMembershipDto.getName());
        savedMembership.setPricePerMonth(inputMembershipDto.getPricePerMonth());
        savedMembership.setContractLengthInWeek(inputMembershipDto.getContractLengthInWeek());
        when(membershipRepository.save(any())).thenReturn(savedMembership);

        MembershipDto createdMembershipDto = membershipService.createMembership(inputMembershipDto);

        verify(membershipRepository, times(1)).save(any());

        assertNotNull(createdMembershipDto);
        assertEquals(savedMembership.getId(), createdMembershipDto.getId());
        assertEquals(savedMembership.getName(), createdMembershipDto.getName());
        assertEquals(savedMembership.getPricePerMonth(), createdMembershipDto.getPricePerMonth());
        assertEquals(savedMembership.getContractLengthInWeek(), createdMembershipDto.getContractLengthInWeek());
    }

    @Test
    @DisplayName("Should get all memberships")
    void getAllMemberships() {
        given(membershipRepository.findAll()).willReturn(List.of(testMembershipOne, testMembershipTwo));

        List<Membership> membershipList = membershipRepository.findAll();
        List<MembershipDto> membershipDtoList = membershipService.getMemberships();

        assertEquals(membershipList.get(0).getName(), membershipDtoList.get(0).getName());
        assertEquals(membershipList.get(0).getPricePerMonth(), membershipDtoList.get(0).getPricePerMonth());
        assertEquals(membershipList.get(0).getContractLengthInWeek(), membershipDtoList.get(0).getContractLengthInWeek());

        assertEquals(membershipList.get(1).getName(), membershipDtoList.get(1).getName());
        assertEquals(membershipList.get(1).getPricePerMonth(), membershipDtoList.get(1).getPricePerMonth());
        assertEquals(membershipList.get(1).getContractLengthInWeek(), membershipDtoList.get(1).getContractLengthInWeek());
    }

    @Test
    @DisplayName("Should update a memberships")
    void updateMembership() {
        given(membershipRepository.findById(anyLong())).willReturn(Optional.of(testMembershipOne));

        MembershipDto membershipDto = membershipService.updateMembership(1L, testMembershipDtoOne);

        assertEquals(membershipDto.getId(), testMembershipDtoOne.getId());
        assertEquals(membershipDto.getName(), testMembershipDtoOne.getName());
        assertEquals(membershipDto.getPricePerMonth(), testMembershipDtoOne.getPricePerMonth());
        assertEquals(membershipDto.getContractLengthInWeek(), testMembershipDtoOne.getContractLengthInWeek());
    }

    @Test
    @DisplayName("Should delete a membership")
    void deleteMembership() {
        given(membershipRepository.findById(anyLong())).willReturn(Optional.ofNullable(testMembershipOne));

        membershipService.deleteMembership(0L);

        verify(membershipRepository, times(1)).deleteById(0L);
        // todo voeg de exception nog toe
    }

    @Test
    @DisplayName("Should subscribe a member")
    void subscribe() {
        given(membershipRepository.findById(1L)).willReturn(Optional.of(testMembershipOne));
        given(memberRepository.findById(memberOne.getEmail())).willReturn(Optional.ofNullable(memberOne));

        membershipService.subscribe(1L, memberOne.getEmail());

        assertEquals(memberOne.getMembership().getName(), testMembershipOne.getName());
        assertEquals(memberOne.getMembership().getPricePerMonth(), testMembershipOne.getPricePerMonth());
        assertEquals(memberOne.getMembership().getContractLengthInWeek(), testMembershipOne.getContractLengthInWeek());
    }

    @Test
    @DisplayName("Should unsubscribe a member")
    void unsubscribe() {
        given(membershipRepository.findById(1L)).willReturn(Optional.of(testMembershipOne));
        given(memberRepository.findById(memberOne.getEmail())).willReturn(Optional.ofNullable(memberOne));

        membershipService.subscribe(1L, memberOne.getEmail());
        membershipService.unsubscribe(memberOne.getEmail());

        assertNull(memberOne.getMembership());
    }
}
package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.MembershipDto;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Membership;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.MembershipRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class MembershipServiceTest {

    @Mock
    MembershipRepository membershipRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    private MembershipService membershipService;
    @Captor
    private ArgumentCaptor<Membership> membershipArgumentCaptor;

    Membership membershipOne = new Membership();
    Membership membershipTwo = new Membership();
    MembershipDto membershipDtoOne = new MembershipDto();
    Member memberOne = new Member();

    @BeforeEach
    void setUp() {
        membershipOne.setName("MembershipOne");
        membershipOne.setPricePerMonth(50);
        membershipOne.setContractLengthInWeek(52);

        membershipDtoOne.setName("MembershipDtoOne");
        membershipDtoOne.setPricePerMonth(50);
        membershipDtoOne.setContractLengthInWeek(52);

        membershipTwo.setName("MembershipTwo");
        membershipTwo.setPricePerMonth(55);
        membershipTwo.setContractLengthInWeek(104);

        memberOne.setEmail("test@mail.com");
        memberOne.setFirstname("Jack");
        memberOne.setLastname("Daniels");
        memberOne.setDateOfBirth(LocalDate.parse("2000-01-01"));
        memberOne.setEnabled(true);

    }

    @Test
    @DisplayName("Should create a new membership")
    void shouldCreateMembership() {
        given(membershipRepository.findById(0L)).willReturn(Optional.of(membershipOne));

        Membership membership = new Membership();
        given(membershipRepository.save(membership)).willReturn(membershipOne);

        membershipRepository.save(membershipOne);

        verify(membershipRepository, times(1)).save(membershipArgumentCaptor.capture());

        Membership captured = membershipArgumentCaptor.getValue();

        assertEquals(membershipOne.getName(), captured.getName());
    }

    @Test
    @DisplayName("Should get all memberships")
    void getAllMemberships() {
        given(membershipRepository.findAll()).willReturn(List.of(membershipOne, membershipTwo));

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
    void updateMembership() {
        given(membershipRepository.findById(anyLong())).willReturn(Optional.ofNullable(membershipOne));

        Optional<Membership> optionalMembership = membershipRepository.findById(1L);

        assertEquals(optionalMembership.get().getId(), 0);
        assertEquals(optionalMembership.get().getName(), membershipOne.getName());
        assertEquals(optionalMembership.get().getPricePerMonth(), membershipOne.getPricePerMonth());
        assertEquals(optionalMembership.get().getContractLengthInWeek(), membershipOne.getContractLengthInWeek());
    }

    @Test
    @DisplayName("Should delete a membership")
    void deleteMembership() {
        given(membershipRepository.findById(anyLong())).willReturn(Optional.ofNullable(membershipOne));

        membershipService.deleteMembership(0L);

        verify(membershipRepository, times(1)).deleteById(0L);
    }

    @Test
    @DisplayName("Should subscribe a member")
    void subscribe() {
        given(membershipRepository.findById(1L)).willReturn(Optional.of(membershipOne));
        given(memberRepository.findById(memberOne.getEmail())).willReturn(Optional.ofNullable(memberOne));

        membershipService.subscribe(1L, memberOne.getEmail());

        assertEquals(memberOne.getMembership().getName(), membershipOne.getName());
        assertEquals(memberOne.getMembership().getPricePerMonth(), membershipOne.getPricePerMonth());
        assertEquals(memberOne.getMembership().getContractLengthInWeek(), membershipOne.getContractLengthInWeek());
    }

    @Test
    @DisplayName("Should unsubscribe a member")
    void unsubscribe() {
        given(membershipRepository.findById(1L)).willReturn(Optional.of(membershipOne));
        given(memberRepository.findById(memberOne.getEmail())).willReturn(Optional.ofNullable(memberOne));

        membershipService.subscribe(1L, memberOne.getEmail());
        membershipService.unsubscribe(memberOne.getEmail());

        assertNull(memberOne.getMembership());
    }
}
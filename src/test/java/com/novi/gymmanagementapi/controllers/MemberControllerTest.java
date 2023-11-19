package com.novi.gymmanagementapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novi.gymmanagementapi.dto.MemberDto;
import com.novi.gymmanagementapi.dto.MemberResponseDto;
import com.novi.gymmanagementapi.dto.UserDto;
import com.novi.gymmanagementapi.models.*;
import com.novi.gymmanagementapi.repositories.AdminRepository;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.MembershipRepository;
import com.novi.gymmanagementapi.services.MemberService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MembershipRepository membershipRepository;

    UserDto userDto = new UserDto();
    Member member = new Member();

    Admin admin = new Admin();
    MemberDto memberDto = new MemberDto();
    MemberDto newMemberDto = new MemberDto();
    MemberResponseDto responseDto = new MemberResponseDto();

    @BeforeEach
    void setUp() {

        Membership membership = new Membership();
        membership.setId(0L);
        membership.setName("Test Membership");
        membership.setPricePerMonth(100);
        membership.setContractLengthInWeek(100);

        Goal goal = new Goal();
        goal.setId(0L);
        goal.setDescription("Test Description");
        goal.setCurrentBodyWeight(100);
        goal.setTargetBodyWeight(90);
        goal.setTargetCalorieIntake(2000);
        goal.setStartDate(LocalDate.parse("2000-01-01"));
        goal.setEndDate(LocalDate.parse("2001-01-01"));
        List<Goal> goalList = new ArrayList<>();
        goalList.add(goal);

        Authority authorityMember = new Authority();
        authorityMember.setEmail(member.getEmail());
        authorityMember.setAuthority("MEMBER");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authorityMember);

        Authority authorityAdmin = new Authority();
        authorityAdmin.setEmail(member.getEmail());
        authorityAdmin.setAuthority("ADMIN");
        Set<Authority> authoritiesAdmin = new HashSet<>();
        authoritiesAdmin.add(authorityAdmin);

        member.setEmail("M1@api.com");
        member.setFirstname("M1");
        member.setLastname("M1");
        member.setDateOfBirth(LocalDate.parse("2000-01-01"));
        member.setPassword("password");
        member.setEnabled(true);
        member.setMembership(membership);
        member.setGoals(goalList);
        //member.setAuthorities(authorities);

        admin.setEmail("A1@api.com");
        admin.setFirstname("admin");
        admin.setLastname("api");
        admin.setEnabled(true);
        admin.setPassword("password");
        admin.setDateOfBirth(LocalDate.parse("1899-01-01"));
        admin.setAuthorities(authoritiesAdmin);

        userDto.setEmail(member.getEmail());
        userDto.setFirstname(member.getFirstname());
        userDto.setLastname(member.getLastname());
        userDto.setDateOfBirth(member.getDateOfBirth());

        memberDto.setEmail(member.getEmail());
        memberDto.setFirstname(member.getFirstname());
        memberDto.setLastname(member.getLastname());
        memberDto.setDateOfBirth(member.getDateOfBirth());
        memberDto.setMembership(member.getMembership());
        memberDto.setPassword(member.getPassword());

        newMemberDto.setEmail("email@test.com");
        newMemberDto.setFirstname("firstname");
        newMemberDto.setLastname("lastname");
        newMemberDto.setDateOfBirth(LocalDate.parse("1999-01-01"));
        newMemberDto.setPassword(member.getPassword());

        responseDto.setEmail(member.getEmail());
        responseDto.setFirstname(member.getFirstname());
        responseDto.setLastname(member.getLastname());
        responseDto.setDateOfBirth(member.getDateOfBirth());
        responseDto.setMembership(member.getMembership());


        membershipRepository.save(membership);
        memberRepository.save(member);
        memberService.addAuthority(member.getEmail(), "MEMBER");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should create a new member account")
    void createMemberAccount() throws Exception {

        memberRepository.deleteAll();

        mockMvc.perform(post("/api/account")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonInputDtoString(memberDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should get a members account")
    void getMemberAccountDetails() throws Exception {

        memberRepository.save(member);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(member.getEmail());

        mockMvc.perform(get("/api/members/account")
                        .principal(mockPrincipal)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").value(member.getEmail()))
                .andExpect(jsonPath("firstname").value(member.getFirstname()))
                .andExpect(jsonPath("lastname").value(member.getLastname()))
                .andExpect(jsonPath("dateOfBirth").value(member.getDateOfBirth().toString()));
    }

    @Test
    @DisplayName("Should update a members account")
    void updateMemberAccount() throws Exception {

        memberRepository.save(member);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(member.getEmail());

        mockMvc.perform(put("/api/members/account")
                        .principal(mockPrincipal)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonInputDtoString(newMemberDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname").value(newMemberDto.getFirstname()))
                .andExpect(jsonPath("lastname").value(newMemberDto.getLastname()))
                .andExpect(jsonPath("dateOfBirth").value(newMemberDto.getDateOfBirth().toString()));
    }

    @Test
    @Disabled
    @DisplayName("Should delete a members account")
    void deleteMemberAccount() throws Exception {
/*
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(member.getEmail());

        mockMvc.perform(delete("/api/members/account")
                        .principal(mockPrincipal));*/
    }

    @Test
    @Disabled
    @DisplayName("Should get a members account as a admin")
    void testGetMemberAccountDetails() {
    }

    @Test
    @Disabled
    @DisplayName("Should update a members account as a admin")
    void testUpdateMemberAccount() {
    }

    @Test
    @Disabled
    @DisplayName("Should delete a members account as a admin")
    void testDeleteMemberAccount() {
    }

    private String asJsonInputDtoString(final MemberDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
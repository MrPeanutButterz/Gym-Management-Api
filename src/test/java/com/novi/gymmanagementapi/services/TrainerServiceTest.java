package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.dto.TrainerDto;
import com.novi.gymmanagementapi.dto.TrainerResponseDto;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.models.Member;
import com.novi.gymmanagementapi.models.Membership;
import com.novi.gymmanagementapi.models.Trainer;
import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.TrainerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class TrainerServiceTest {

    @Mock
    TrainerRepository trainerRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    private TrainerService trainerService;

    Trainer trainerOne = new Trainer();
    Trainer trainerTwo = new Trainer();

    TrainerDto trainerDtoOne = new TrainerDto();
    TrainerDto trainerDtoTwo = new TrainerDto();

    Member member = new Member();
    Membership membership = new Membership();
    Set<Authority> authorities = new HashSet<>();

    @BeforeEach
    void setUp() {
        trainerOne.setEmail("t1@mock.api");
        trainerOne.setFirstname("T");
        trainerOne.setLastname("O");
        trainerOne.setDateOfBirth(LocalDate.parse("2000-01-01"));
        trainerOne.setEnabled(true);
        trainerOne.setHourlyRate(80.01);
        trainerOne.setPassword("password");

        trainerTwo.setEmail("t2@mock.api");
        trainerTwo.setFirstname("T");
        trainerTwo.setLastname("T");
        trainerTwo.setDateOfBirth(LocalDate.parse("2000-02-02"));
        trainerTwo.setEnabled(true);
        trainerTwo.setHourlyRate(80.02);
        trainerTwo.setPassword("password");

        member.setEmail("m1@mock.api");
        member.setFirstname("M");
        member.setLastname("O");
        member.setDateOfBirth(LocalDate.parse("2000-01-01"));
        member.setTrainer(trainerTwo);
        member.setMembership(membership);

        // assert
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should get all trainers")
    void getTrainers() {
        given(trainerRepository.findAll()).willReturn(List.of(trainerOne, trainerTwo));

        List<TrainerResponseDto> trainers = trainerService.getTrainers();

        assertEquals(trainers.get(0).getEmail(), trainerOne.getEmail());
        assertEquals(trainers.get(0).getFirstname(), trainerOne.getFirstname());
        assertEquals(trainers.get(0).getLastname(), trainerOne.getLastname());
        assertEquals(trainers.get(0).getDateOfBirth(), trainerOne.getDateOfBirth());
        assertEquals(trainers.get(0).getHourlyRate(), trainerOne.getHourlyRate());

        assertEquals(trainers.get(1).getEmail(), trainerTwo.getEmail());
        assertEquals(trainers.get(1).getFirstname(), trainerTwo.getFirstname());
        assertEquals(trainers.get(1).getLastname(), trainerTwo.getLastname());
        assertEquals(trainers.get(1).getDateOfBirth(), trainerTwo.getDateOfBirth());
        assertEquals(trainers.get(1).getHourlyRate(), trainerTwo.getHourlyRate());
    }

    @Test
    @DisplayName("Should assign a trainer to a member")
    void assignTrainer() {
        given(trainerRepository.findById(trainerOne.getEmail())).willReturn(Optional.ofNullable(trainerOne));
        given(memberRepository.findById(member.getEmail())).willReturn(Optional.of(member));

        trainerService.assignTrainer(member.getEmail(), trainerOne.getEmail());

        assertEquals(member.getTrainer().getEmail(), trainerOne.getEmail());
        assertEquals(member.getTrainer().getFirstname(), trainerOne.getFirstname());
        assertEquals(member.getTrainer().getLastname(), trainerOne.getLastname());
        assertEquals(member.getTrainer().getDateOfBirth(), trainerOne.getDateOfBirth());
        assertEquals(member.getTrainer().getHourlyRate(), trainerOne.getHourlyRate());
    }

    @Test
    @DisplayName("Should dismiss a trainer from a member")
    void dismissTrainer() {
        given(memberRepository.findById(member.getEmail())).willReturn(Optional.of(member));

        trainerService.dismissTrainer(member.getEmail());

        assertNull(member.getTrainer());
    }

    @Test
    @DisplayName("Should get trainer account")
    void getTrainerAccount() {
        given(trainerRepository.findById(trainerOne.getEmail())).willReturn(Optional.ofNullable(trainerOne));

        TrainerResponseDto trainer = trainerService.getTrainerAccount(trainerOne.getEmail());

        assertEquals(trainerOne.getEmail(), trainer.getEmail());
        assertEquals(trainerOne.getFirstname(), trainer.getFirstname());
        assertEquals(trainerOne.getLastname(), trainer.getLastname());
        assertEquals(trainerOne.getDateOfBirth(), trainer.getDateOfBirth());
        assertEquals(trainerOne.getHourlyRate(), trainer.getHourlyRate());
    }

    @Test
    @Disabled
    @DisplayName("Should create a new trainer")
    void createTrainerAccount() {
    }

    @Test
    @Disabled
    @DisplayName("Should update a trainer")
    void updateTrainer() {
        trainerDtoOne.setEmail(trainerOne.getEmail());
        trainerDtoOne.setFirstname(trainerOne.getFirstname());
        trainerDtoOne.setLastname(trainerOne.getLastname());
        trainerDtoOne.setDateOfBirth(trainerOne.getDateOfBirth());
        trainerDtoOne.setEnabled(trainerOne.isEnabled());
        trainerDtoOne.setHourlyRate(trainerOne.getHourlyRate());
        trainerDtoOne.setPassword(trainerOne.getPassword());

        given(trainerRepository.findById(any())).willReturn(Optional.of(trainerOne));

        TrainerResponseDto dto = trainerService.updateTrainer(trainerOne.getEmail(), trainerDtoOne);

        assertEquals(dto.getEmail(), trainerDtoOne.getEmail());
        assertEquals(dto.getFirstname(), trainerDtoOne.getFirstname());
        assertEquals(dto.getLastname(), trainerDtoOne.getLastname());
        assertEquals(dto.getDateOfBirth(), trainerDtoOne.getDateOfBirth());
    }

    @Test
    @Disabled
    @DisplayName("Should disable a trainer account")
    void disableTrainerAccount() {
    }

    @Test
    @Disabled
    @DisplayName("Should get all trainer clients")
    void getClients() {
    }

    @Test
    @Disabled
    @DisplayName("Should remove a authority")
    void removeAuthority() {
    }

    @Test
    @Disabled
    @DisplayName("Should add a authority")
    void addAuthority() {
    }
}
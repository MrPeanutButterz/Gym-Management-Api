package com.novi.gymmanagementapi.services;

import com.novi.gymmanagementapi.repositories.MemberRepository;
import com.novi.gymmanagementapi.repositories.TrainerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class TrainerServiceTest {

    @Mock
    TrainerRepository trainerRepository;
    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should get all trainers")
    void getTrainers() {
    }

    @Test
    @DisplayName("Should assign a trainer to a member")
    void assignTrainer() {
    }

    @Test
    @DisplayName("Should dismiss a trainer from a member")
    void dismissTrainer() {
    }

    @Test
    @DisplayName("Should get trainer account")
    void getTrainerAccount() {
    }

    @Test
    @DisplayName("Should create a new trainer")
    void createTrainerAccount() {
    }

    @Test
    @DisplayName("Should create a trainer")
    void updateTrainer() {
    }

    @Test
    @DisplayName("Should disable a trainer account")
    void disableTrainerAccount() {
    }

    @Test
    @DisplayName("Should get all trainer clients")
    void getClients() {
    }

    @Test
    @DisplayName("Should remove a authority")
    void removeAuthority() {
    }

    @Test
    @DisplayName("Should add a authority")
    void addAuthority() {
    }
}
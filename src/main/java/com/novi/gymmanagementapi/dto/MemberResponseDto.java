package com.novi.gymmanagementapi.dto;

import com.novi.gymmanagementapi.models.Membership;

import java.util.List;

public class MemberResponseDto extends UserDto {

    private Membership membership;
    private TrainerResponseDto trainer;
    private List<Long> goalIDs;

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public TrainerResponseDto getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerResponseDto trainer) {
        this.trainer = trainer;
    }

    public List<Long> getGoalIDs() {
        return goalIDs;
    }

    public void setGoalIDs(List<Long> goalIDs) {
        this.goalIDs = goalIDs;
    }
}

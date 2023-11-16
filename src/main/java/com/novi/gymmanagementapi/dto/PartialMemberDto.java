package com.novi.gymmanagementapi.dto;

import com.novi.gymmanagementapi.models.Membership;

import java.util.List;

public class PartialMemberDto extends UserDto {

    private Membership membership;
    private PartialTrainerDto trainer;
    private List<Long> goalIDs;

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public PartialTrainerDto getTrainer() {
        return trainer;
    }

    public void setTrainer(PartialTrainerDto trainer) {
        this.trainer = trainer;
    }

    public List<Long> getGoalIDs() {
        return goalIDs;
    }

    public void setGoalIDs(List<Long> goalIDs) {
        this.goalIDs = goalIDs;
    }
}

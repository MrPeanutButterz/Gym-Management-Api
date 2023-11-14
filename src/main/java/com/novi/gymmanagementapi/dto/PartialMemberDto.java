package com.novi.gymmanagementapi.dto;

import com.novi.gymmanagementapi.models.Goal;
import com.novi.gymmanagementapi.models.Membership;

import java.util.List;

public class PartialMemberDto extends UserDto {

    private Membership membership;
    private PartialTrainerDto trainer;
    private List<Goal> goals;

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

    public List<Goal> getGoals() { return goals; }

    public void setGoals(List<Goal> goals) { this.goals = goals; }
}

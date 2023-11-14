package com.novi.gymmanagementapi.dto;

import com.novi.gymmanagementapi.models.Membership;

public class FullMemberDto extends UserDto {

    private String password;
    private Membership membership;
    private PartTrainerDto trainer;

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public PartTrainerDto getTrainer() {
        return trainer;
    }

    public void setTrainer(PartTrainerDto trainer) {
        this.trainer = trainer;
    }
}

package com.novi.gymmanagementapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.novi.gymmanagementapi.models.Membership;
import com.novi.gymmanagementapi.models.Trainer;

public class MemberDto extends UserDto {

    private Membership membership;
    @JsonIgnore
    private Trainer trainer;

    private String trainedBy;

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Trainer getTrainer() { return trainer; }

    public void setTrainer(Trainer trainer) { this.trainer = trainer; }

    public String getTrainedBy() {
        return trainedBy;
    }

    public void setTrainedBy(String firstname, String lastname) {
        this.trainedBy = firstname + " " + lastname;
    }
}

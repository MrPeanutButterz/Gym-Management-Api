package com.novi.gymmanagementapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member extends User {

    @ManyToOne(fetch = FetchType.EAGER)
    private Membership membership;

    @ManyToOne(fetch = FetchType.EAGER)
    private Trainer trainer;

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Trainer getTrainer() { return trainer; }

    public void setTrainer(Trainer trainer) { this.trainer = trainer; }
}

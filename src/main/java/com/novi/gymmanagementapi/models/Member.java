package com.novi.gymmanagementapi.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
public class Member extends User {

    @ManyToOne(fetch = FetchType.EAGER)
    private Membership membership;
    @ManyToOne(fetch = FetchType.EAGER)
    private Trainer trainer;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_email", referencedColumnName = "email")
    private List<Goal> goals;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProfilePicture profilePicture;

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    public List<Long> getGoalIDs() {
        // transforms a list of workouts to a list of workout IDs
        List<Long> IDs = new ArrayList<>();
        for (Goal g : this.goals) {
            IDs.add(g.getId());
        }
        return IDs;
    }
}

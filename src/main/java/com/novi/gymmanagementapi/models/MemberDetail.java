package com.novi.gymmanagementapi.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "member_details")
public class MemberDetail {

    @Id
    @GeneratedValue
    private Long id;
    private boolean contractIsActive;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private Membership membership;
/*    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_email", referencedColumnName = "email")
    private List<Goal> goals;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isContractIsActive() {
        return contractIsActive;
    }

    public void setContractIsActive(boolean contractIsActive) {
        this.contractIsActive = contractIsActive;
    }

    public LocalDate getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(LocalDate contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public LocalDate getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(LocalDate contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

/*    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }*/
}

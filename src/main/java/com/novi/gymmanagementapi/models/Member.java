package com.novi.gymmanagementapi.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "members")
public class Member extends User {

    @ManyToOne(fetch = FetchType.EAGER)
    private Membership membership;

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}

package com.novi.gymmanagementapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull
    private String password;
    @Column(nullable = false)
    private boolean enabled = true;
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "email",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();
    @OneToOne
    private ContractInformation contractInformation;
    @OneToOne(mappedBy = "id")
    private MemberDetail memberDetail;
    @OneToOne(mappedBy = "id")
    private TrainerDetail trainerDetail;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

    public ContractInformation getContractInformation() {
        return contractInformation;
    }

    public void setContractInformation(ContractInformation contractInformation) {
        this.contractInformation = contractInformation;
    }

    public MemberDetail getMemberDetail() {
        return memberDetail;
    }

    public void setMemberDetail(MemberDetail memberDetail) {
        this.memberDetail = memberDetail;
    }

    public TrainerDetail getTrainerDetail() {
        return trainerDetail;
    }

    public void setTrainerDetail(TrainerDetail trainerDetail) {
        this.trainerDetail = trainerDetail;
    }
}

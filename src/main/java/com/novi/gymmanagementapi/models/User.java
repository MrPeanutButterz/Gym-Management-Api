package com.novi.gymmanagementapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull
    @Size(min = 6, max = 120)
    private String password;
    @NotNull
    private boolean enabled = true;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProfilePicture profilePicture;
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "email",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();
    @OneToOne
    private MemberDetail memberDetail;
    @OneToOne
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
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

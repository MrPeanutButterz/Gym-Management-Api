package com.novi.gymmanagementapi.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.novi.gymmanagementapi.models.Authority;
import com.novi.gymmanagementapi.utilties.ResponseViews;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TrainerDto {

    @JsonView(ResponseViews.MyResponseView.class)
    private String email;
    private String password;
    @JsonView(ResponseViews.MyResponseView.class)
    private String firstname;
    @JsonView(ResponseViews.MyResponseView.class)
    private String lastname;
    @JsonView(ResponseViews.MyResponseView.class)
    private Date dateOfBirth;
    @JsonView(ResponseViews.MyResponseView.class)
    private double hourlyRate;
    private boolean enabled = true;
    private Set<Authority> authorities = new HashSet<>();

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}

package com.novi.gymmanagementapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserDto {

    @Email
    @NotNull
    private String email;
    @NotNull
    @Size(min = 2, max = 30)
    private String firstname;
    @NotNull
    @Size(min = 2, max = 30)
    private String lastname;
    @NotNull
    @Past
    private LocalDate dateOfBirth;

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
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
}

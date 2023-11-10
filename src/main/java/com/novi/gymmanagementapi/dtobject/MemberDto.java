package com.novi.gymmanagementapi.dtobject;

import com.novi.gymmanagementapi.models.Authority;

import java.util.Set;

public class MemberDto {

    public String email;
    public String password;
    public Boolean enabled;
    public Set<Authority> authorities;

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}

package com.novi.gymmanagementapi.dto;

public class FullMemberDto extends PartialMemberDto {

    private String password;
    private boolean enabled;

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}

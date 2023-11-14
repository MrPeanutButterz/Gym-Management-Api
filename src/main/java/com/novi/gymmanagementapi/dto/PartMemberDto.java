package com.novi.gymmanagementapi.dto;

import com.novi.gymmanagementapi.models.Membership;

public class PartMemberDto extends UserDto {

    private Membership membership;

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

}

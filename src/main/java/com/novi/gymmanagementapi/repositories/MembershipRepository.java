package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}

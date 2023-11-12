package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
